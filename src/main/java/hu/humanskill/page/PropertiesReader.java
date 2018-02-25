package hu.humanskill.page;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class PropertiesReader {

    protected HashMap<String, Object> read(String language) {
        if (language == null) {
            language = "en";
        }
        HashMap<String, Object> params = new HashMap<>();
        Locale locale = new Locale(language);
        MyResourceBundle lang = new MyResourceBundle(locale);
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

        return params;
    }
}
