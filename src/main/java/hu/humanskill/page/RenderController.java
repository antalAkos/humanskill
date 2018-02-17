package hu.humanskill.page;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class RenderController {

    public static ModelAndView renderIndexPage(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "index2");
    }
}
