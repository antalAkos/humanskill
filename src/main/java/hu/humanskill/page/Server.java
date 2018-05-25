package hu.humanskill.page;

import org.eclipse.jetty.http.HttpStatus;
import static spark.Spark.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.io.File;

import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;
import static spark.Spark.*;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static RenderController renderController = new RenderController();
    public static void main(String[] args) throws IllegalArgumentException {

        // default server settings
        logger.info("Starting server..");
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(getHerokuAssignedPort());




        get("/", renderController::renderIndexPage, new ThymeleafTemplateEngine());
        get("/:lang", renderController::renderIndexHu, new ThymeleafTemplateEngine());
        post("/save-cv", (req, res) -> renderController.saveCV(req, res));
        get("*",  (req, res) -> "Go back - invalid page");



    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}


