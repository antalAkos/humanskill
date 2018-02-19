package hu.humanskill.page;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


public class RenderController {

    //private static ResourceBundle text = ResourceBundle.getBundle("languages/en_GB");

    public static ModelAndView renderIndexPage(Request req, Response res) {
        Map params = new HashMap<>();
        //System.out.println(text.getString("menu1"));
        //params.put("menu1", text.getString("menu1"));
        return new ModelAndView(params, "index");
    }
}
