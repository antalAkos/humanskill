package hu.humanskill.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IllegalArgumentException {

        // default server settings
        logger.info("Starting server..");
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(7000);


        get("/", RenderController::renderIndexPage, new ThymeleafTemplateEngine());

    }
}


