package hu.humanskill.page;

import hu.humanskill.page.service.ApplyService;
import hu.humanskill.page.service.UserService;
import org.eclipse.jetty.http.HttpStatus;
import static spark.Spark.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.servlet.SparkApplication;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;

import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;

public class Server  {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("humanskill");
    private static final EntityManager em = emf.createEntityManager();
    private static Utility utility = new Utility();
    private static UserService userService = new UserService(em, utility );
    private static ApplyService applyService = new ApplyService(em);
    private static PropertiesReader propertiesReader = new PropertiesReader();
    private static RenderController renderController = new RenderController(applyService, propertiesReader, userService);




    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {
        //userService.createAdmin("admin", "matepapa");
        //userService.createApply();
        logger.info("Starting server..");
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        port(8082);

        staticFileLocation("/public");
        //secure();

        get("/", renderController::renderIndexPage, new ThymeleafTemplateEngine());
        get("/mezogazdasag", renderController::renderAgric);
        get("/festo", renderController::renderPainter);
        get("/hegeszto", renderController::renderWelder);
        get("/allasok", renderController::renderJobs, new ThymeleafTemplateEngine());
        get("/admin", renderController::renderAdmin, new ThymeleafTemplateEngine());
        get("/:lang", renderController::renderByLanguage, new ThymeleafTemplateEngine());
        get("/download/:id", renderController:: getFile);
        get("/delete/:id", renderController:: delete, new ThymeleafTemplateEngine());
        post("/addname", (req, res) -> renderController.addNumber(req, res));
        post("/save-cv", (req, res) -> renderController.saveCV(req, res));
        post("/login", renderController :: login, new ThymeleafTemplateEngine());





        //get("*",  (req, res) -> "Go back - invalid page");

    }
}


