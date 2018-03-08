package hu.humanskill.page;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.*;


public class RenderController {

    PropertiesReader propertiesReader = new PropertiesReader();

    public ModelAndView renderIndexPage(Request req, Response res) {
        Map<String, Object> params = propertiesReader.read(null);

        return new ModelAndView(params, "index");

    }

    public ModelAndView renderIndexHu(Request req, Response res) {
        String lang = req.params(":lang");
        HashMap<String, Object> params = propertiesReader.read(lang);

        return new ModelAndView(params, "index");
    }



}
