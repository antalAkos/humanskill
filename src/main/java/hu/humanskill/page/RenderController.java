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
    //SendAttachmentEmail sendAttachmentEmail = new SendAttachmentEmail();
    private ApplyService applyService;
    private UserService userService;

    public RenderController(ApplyService applyService, PropertiesReader propertiesReader, UserService userService) {
        this.applyService = applyService;
        this.propertiesReader = propertiesReader;
        this.userService = userService;
    }

    public ModelAndView renderIndexPage(Request req, Response res) {
        Map<String, Object> params = propertiesReader.read(null);
        System.out.println(req.headers("Accept-Language"));
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

     public Object saveCV(Request req, Response res)  {
        //Set<String> params = req.queryParams();
        try {

            Path tempFile = Files.createTempFile(Paths.get("src","main", "resources", "public", "upload"), "", "");
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    new MultipartConfigElement("/tmp"));

            //String filename = req.raw().getPart("file-706").getSubmittedFileName();

            Part uploadedFile = req.raw().getPart("file-706");
            //sendAttachmentEmail.sendMail(params, filename);
            try (final InputStream in = uploadedFile.getInputStream()) {
                OutputStream outputStream = new FileOutputStream(tempFile + uploadedFile.getSubmittedFileName());
                IOUtils.copy(in, outputStream);
                outputStream.close();
                //Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            Apply application = new Apply(req.queryParams("name"), req.queryParams("email"), req.queryParams("phone"),  uploadedFile.getSubmittedFileName());
            applyService.save(application);
            String referer = req.headers("Referer");
            if(referer != null){
                String redirectTo = referer;
            }

            uploadedFile.delete();
            res.redirect("/");
            return "";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public ModelAndView renderApply(Request request, Response response) {
            HashMap<String, Object> params = new HashMap();
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
}
