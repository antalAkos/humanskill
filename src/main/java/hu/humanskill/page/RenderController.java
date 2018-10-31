package hu.humanskill.page;

import com.google.gson.Gson;
import hu.humanskill.page.model.Apply;
import hu.humanskill.page.service.ApplyService;
import hu.humanskill.page.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.utils.IOUtils;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class RenderController {

    private static final Logger logger = LoggerFactory.getLogger(RenderController.class);
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

     public String addNumber(Request req, Response res)  {
        //res.type("text/json");
         Apply application;
        try {
            /*req.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    new MultipartConfigElement("/tmp"));
            Part uploadedFile = req.raw().getPart("file-706");
            String filename = uploadedFile.getSubmittedFileName();
            if (filename.length() > 0) {
                /*Path tempFile = Files.createTempFile(Paths.get("src","main", "resources", "public", "upload"),
                        filename.substring(0, filename.lastIndexOf(".")), filename.substring(filename.lastIndexOf(".")));

                try (final InputStream in = uploadedFile.getInputStream()) {
                    OutputStream outputStream = new FileOutputStream(tempFile.toFile());
                    IOUtils.copy(in, outputStream);
                    outputStream.close();
                }
                 application = new Apply(req.queryParams("name"), req.queryParams("email"), req.queryParams("phone"), "/" + tempFile.subpath(5,6));
            } else{
            }*/

            Gson g = new Gson();
            HashMap<String, String> phoneData = g.fromJson(req.body(), HashMap.class);

            application = new Apply(req.queryParams("name"), req.queryParams("email"), phoneData.get("phone"), "");
            applyService.save(application);

            String referer = req.headers("Referer");
            if(referer != null){
                String redirectTo = referer;
                res.redirect(redirectTo);
            }
            //uploadedFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
             res.status(500);

        }
         return "";

    }

    public ModelAndView renderAgric(Request request, Response response) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("apply", true);
        return new ModelAndView(params, "landing");

    }

    public ModelAndView renderPainter(Request request, Response response) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("apply", true);
        return new ModelAndView(params, "festo");

    }

    public ModelAndView renderAdmin(Request request, Response response) {

        return new ModelAndView(new HashMap<>(),"admin");
    }


    public ModelAndView login(Request req, Response res) {
        HashMap<String, Object> params = new HashMap<>();
        if (userService.canLogIn(req.queryParams("username"), req.queryParams("password"))) {
                req.session().attribute("name", req.queryParams("username"));

        }

        return renderLogin(req, res);
    }



    public ModelAndView renderLogin(Request request, Response response) {
        Map params = new HashMap<>();
        params.put("isLogged", request.session().attribute("name")); // ha ez true, van session, ha nem, akkor nincs -
        params.put("login", true);
        params.put("applies", userService.getAll());
        for (Apply apply: userService.getAll()) {
            System.out.println(apply.getPhone());
        }
        return new ModelAndView(params, "admin");
    }


    public Response getFile(Request request, Response response)  {

        String filename = applyService.getOne(Long.parseLong(request.params(":id"))).getFilename();
        if (filename.length() > 0) {
            File file = new File(filename);
            response.type("application/download");
            response.header("Content-disposition", "attachment; filename=" + file.getName());
            System.out.println(file.getAbsolutePath());

            try (OutputStream outputStream = response.raw().getOutputStream()) {
                outputStream.write(Files.readAllBytes(file.toPath()));
                outputStream.flush();

            } catch (IOException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    public ModelAndView delete(Request request, Response response) {
        HashMap<String, Object> params = new HashMap<String, Object>();

        Long id = Long.parseLong(request.params(":id"));
        applyService.remove(id);
        return renderLogin(request, response);
    }

    public String saveCV(Request req, Response res) {
        Apply application;
        try {
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    new MultipartConfigElement("/tmp"));
            Part uploadedFile = req.raw().getPart("file-706");
            String filename = uploadedFile.getSubmittedFileName();
            if (filename.length() > 0) {
                Path tempFile = Files.createTempFile(Paths.get(new File(".").getAbsolutePath() + "/uploads"),
                        filename.substring(0, filename.lastIndexOf(".")), filename.substring(filename.lastIndexOf(".")));

                try (final InputStream in = uploadedFile.getInputStream()) {
                    OutputStream outputStream = new FileOutputStream(tempFile.toFile());
                    IOUtils.copy(in, outputStream);
                    outputStream.close();
                }
                 application = new Apply(req.queryParams("name"), req.queryParams("email"), req.queryParams("phone"), tempFile.toString());
                System.out.println(tempFile.toString());
            } else{
                application = new Apply(req.queryParams("name"), req.queryParams("email"), req.queryParams("phone"), "");

            }

            applyService.save(application);

            String referer = req.headers("Referer");
            if(referer != null){
                String redirectTo = referer;
                res.redirect(redirectTo);
            }
            //uploadedFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
            res.status(500);

        }
        return "";
    }
}
