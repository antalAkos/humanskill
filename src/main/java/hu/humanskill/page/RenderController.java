package hu.humanskill.page;

import org.eclipse.jetty.http.HttpStatus;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import spark.utils.IOUtils;

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

    PropertiesReader propertiesReader = new PropertiesReader();
    SendAttachmentEmail sendAttachmentEmail = new SendAttachmentEmail();

    public ModelAndView renderIndexPage(Request req, Response res) {
        Map<String, Object> params = propertiesReader.read(null);

        return new ModelAndView(params, "index");

    }

    public ModelAndView renderIndexHu(Request req, Response res) {
        String lang = req.params(":lang");
        HashMap<String, Object> params = propertiesReader.read(lang);

        return new ModelAndView(params, "index");
    }

     public Object saveCV(Request req, Response res)  {
        Map params = req.params();
        try {

            Path tempFile = Files.createTempFile(Paths.get("src","main", "resources", "public", "upload"), "", "");
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    new MultipartConfigElement("/tmp"));

            String filename = req.raw().getPart("file-706").getSubmittedFileName();

            Part uploadedFile = req.raw().getPart("file-706");
            //sendAttachmentEmail.sendMail(params, filename);
            try (final InputStream in = uploadedFile.getInputStream()) {
                Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            String referer = req.headers("Referer");
            if(referer != null){
                String redirectTo = referer;
            }
            //uploadedFile.delete();
            res.redirect(referer);
            return "";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



}
