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


    public ModelAndView renderIndexPage(Request req, Response res) {
        Map<String, Object> params = new HashMap<>();

        Locale eng = new Locale("en");
        ResourceBundle lang = ResourceBundle.getBundle("templates/index", eng);
        for (String key: lang.keySet()) {
            params.put(key, lang.getString(key));
        }
        params.put("stepsList",
                new ArrayList<>(Arrays.asList(lang.getString("stepsList").split(",")))
                );
        params.put("advantagesList",
                new ArrayList<>(Arrays.asList(lang.getString("advantagesList").split(",")))
        );

        return new ModelAndView(params, "index");

    }

    public ModelAndView renderIndexHu(Request req, Response res) {
        Map<String, String> params = new HashMap<>();

        Locale hun = new Locale(req.params(":lang"));
        ResourceBundle lang = ResourceBundle.getBundle("templates/index", hun);
        Enumeration<String> keys = lang.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            params.put(key,lang.getString(key));
            String value = lang.getString(key);
            System.out.println(key + ": " + value);
        }

        return new ModelAndView(params, "index");
    }



}
