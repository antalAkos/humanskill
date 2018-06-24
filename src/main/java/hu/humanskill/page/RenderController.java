package hu.humanskill.page;

import hu.humanskill.page.model.Apply;
import hu.humanskill.page.service.ApplyService;
import hu.humanskill.page.service.UserService;
import org.eclipse.jetty.http.HttpStatus;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import spark.utils.IOUtils;

import javax.jws.WebParam;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static spark.Spark.redirect;


public class RenderController {

    private PropertiesReader propertiesReader;
    private ApplyService applyService;
    private UserService userService;

    public RenderController(ApplyService applyService, PropertiesReader propertiesReader, UserService userService) {
        this.applyService = applyService;
        this.propertiesReader = propertiesReader;
        this.userService = userService;
    }

    public ModelAndView renderIndexPage(Request req, Response res) {
        Map<String, Object> params = propertiesReader.read(null);
        if (req.headers("Accept-Language").contains("hu")) {
            res.redirect("/hu");
        } else if (req.headers("Accept-Language").contains("ro")) {
            res.redirect("/ro");
        } else if(req.headers("Accept-Language").contains("ru")) {
            res.redirect("/ru");
        }
        return new ModelAndView(params, "index");

    }

    public ModelAndView renderByLanguage(Request req, Response res) {
        String lang = req.params(":lang");
        HashMap<String, Object> params = propertiesReader.read(lang);
        return new ModelAndView(params, "index");
    }

     public String saveCV(Request req, Response res)  {
        //res.type("text/json");
         Apply application;
        try {
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    new MultipartConfigElement("/tmp"));
            Part uploadedFile = req.raw().getPart("file-706");
            String filename = uploadedFile.getSubmittedFileName();
            if (filename.length() > 0) {
                Path tempFile = Files.createTempFile(Paths.get("src","main", "resources", "public", "upload"),
                        filename.substring(0, filename.lastIndexOf(".")), filename.substring(filename.lastIndexOf(".")));

                try (final InputStream in = uploadedFile.getInputStream()) {
                    OutputStream outputStream = new FileOutputStream(tempFile.toFile());
                    IOUtils.copy(in, outputStream);
                    outputStream.close();
                }
                 application = new Apply(req.queryParams("name"), req.queryParams("email"), req.queryParams("phone"), "/" + tempFile.subpath(5,6));
            } else{
                 application = new Apply(req.queryParams("name"), req.queryParams("email"), req.queryParams("phone"), "");
            }

            applyService.save(application);

            String referer = req.headers("Referer");
            if(referer != null){
                String redirectTo = referer;
                res.redirect(redirectTo);
            }
            uploadedFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
             res.status(500);

        }
         return "";

    }

    public ModelAndView renderApply(Request request, Response response) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("apply", true);
        return new ModelAndView(params, "landing");

    }

    public ModelAndView renderAdmin(Request request, Response response) {

        return new ModelAndView(new HashMap<>(),"admin");
    }


    public ModelAndView login(Request req, Response res) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        if (userService.canLogIn(req.queryParams("username"), req.queryParams("password"))) {
            req.session().attribute("name", req.queryParams("username"));
            List<Apply> applies = userService.getAll();
            params.put("login", true);
            params.put("applies", applies);
            return new ModelAndView(params, "admin");

        }

        return new ModelAndView(params, "apply");
    }

    public Response getFile(Request request, Response response)  {

        String filename = request.params(":filename");
        if (filename.length() > 0) {
            response.header("Content-disposition", "attachment; filename=" + filename);

            File file = new File("src/main/resources/public/files/upload/" + filename);
            try (OutputStream outputStream = response.raw().getOutputStream()) {
                outputStream.write(Files.readAllBytes(file.toPath()));
                outputStream.flush();

            } catch (IOException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    public Object delete(Request request, Response response) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        Long id = Long.parseLong(request.params(":id"));
        applyService.remove(id);
        List<Apply> applies = userService.getAll();
        params.put("login", true);
        params.put("applies", applies);
        response.redirect("/login");
        return "";
    }
}
