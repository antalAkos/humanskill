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
        params.put("resultList",
                new ArrayList<>(Arrays.asList(lang.getString("resultList").split(",")))
        );
        params.put("firmDetails",
                new ArrayList<>(Arrays.asList(lang.getString("firmDetails").split(",")))
        );

        return new ModelAndView(params, "index");

    }

    public ModelAndView renderIndexHu(Request req, Response res) {
        Map<String, String> params = new HashMap<>();

        Locale locale = new Locale(req.params(":lang"));
        ResourceBundle lang = ResourceBundle.getBundle("templates/index", locale);
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
