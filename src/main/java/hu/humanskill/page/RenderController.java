package hu.humanskill.page;

import com.google.gson.Gson;
import hu.humanskill.page.model.Apply;
import hu.humanskill.page.service.ApplyService;
import hu.humanskill.page.service.UserService;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
import java.sql.Timestamp;
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
        } else{
            res.redirect("/en");
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
            Date time = new Date();

            application = new Apply(req.queryParams("name"), phoneData.get("job"), req.queryParams("email"), phoneData.get("phone"), "",  new Timestamp(time.getTime()));
            applyService.save(application);
            res.status(200);

            /*String referer = req.headers("Referer");
            if(referer != null){
                String redirectTo = referer;
                res.redirect(redirectTo);
            }*/
            //uploadedFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
            res.body(ExceptionUtils.getStackTrace(e));
            //throw new RuntimeException(e);
             res.status(500);
             logger.error(ExceptionUtils.getStackTrace(e));

        }
         return "";

    }

    public Object renderAgric(Request request, Response response) {

        response.redirect("/mezogazdasag.html"); return null;
    }

    public Object renderPainter(Request request, Response response) {

        response.redirect("/festo.html"); return null;

    }

    public Object renderWelder(Request request, Response response) {

        response.redirect("/hegeszto.html"); return null;
    }

    public Object renderElectrician(Request request, Response response) {

        response.redirect("/villanyszerelo.html"); return null;
    }

    public Object renderWindowmaker(Request request, Response response) {

        response.redirect("/asztalos.html"); return null;
    }

    public Object renderBricklayer(Request request, Response response) {

        response.redirect("/komuves.html"); return null;
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
        Date time = new Date();
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
                 application = new Apply(req.queryParams("name"), "", req.queryParams("email"), req.queryParams("phone"), tempFile.toString(), new Timestamp(time.getTime()));
                System.out.println(tempFile.toString());
            } else{
                application = new Apply(req.queryParams("name"), "", req.queryParams("email"), req.queryParams("phone"), "", new Timestamp(time.getTime()));

            }

            applyService.save(application);
            res.status(200);

            /*String referer = req.headers("Referer");
            if(referer != null){
                res.status(200);

                String redirectTo = referer;
                res.redirect(redirectTo);
            }*/
            //uploadedFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
            res.status(500);

        }
        return "";
    }



    public ModelAndView renderJobs(Request request, Response response) {
        HashMap params = new HashMap<>();
        params.put("page", "ms-main");
        params.put("status", "normal");
        return new ModelAndView(params, "ms-microsite");
    }

    public ModelAndView renderJobCategory(Request request, Response response) {
        String jobCaregory = request.params(":job-category");
        HashMap params = new HashMap<>();
        switch (jobCaregory) {
            case "jelentkezes":
                return renderSuccessPage(request, response);
            case "hiba":
                return renderFailPage(request, response);
            default:
                return renderJobs(request, response);
        }

    }

    public Object renderButcher(Request request, Response response) {
        response.redirect("/hentes.html"); return null;

    }

    public Object renderButcher2(Request request, Response response) {
        response.redirect("/hentes-betanitott.html"); return null;

    }

    public ModelAndView renderSuccessPage(Request request, Response response) {
        String jobCaregory = request.params(":job-category");
        HashMap params = new HashMap<>();
        jobCaregory = jobCaregory.equals("jelentkezes") ? "ms-microsite":jobCaregory;
        params.put("page", jobCaregory);
        params.put("status", "success");
        return new ModelAndView(params, "ms-microsite");
    }

    public ModelAndView renderFailPage(Request request, Response response) {
        String jobCaregory = request.params(":job-category");
        HashMap params = new HashMap<>();
        jobCaregory = jobCaregory.equals("hiba") ? "ms-microsite":jobCaregory;
        params.put("page", jobCaregory);
        params.put("status", "fail");
        return new ModelAndView(params, "ms-microsite");
    }
}
