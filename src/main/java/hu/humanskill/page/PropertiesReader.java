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
        if (language.equals("hu")) {
            params.put("hun", true);
        }
        params.put("stepsList",
                new ArrayList<>(Arrays.asList(lang.getString("stepsList").split(",")))
        );
        params.put("advantagesList",
                new ArrayList<>(Arrays.asList(lang.getString("advantagesList").split("#")))
        );
        params.put("resultList",
                new ArrayList<>(Arrays.asList(lang.getString("resultList").split(",")))
        );
        params.put("firmDetails",
                new ArrayList<>(Arrays.asList(lang.getString("firmDetails").split("#")))
        );
        params.put("coachingList",
                new ArrayList<>(Arrays.asList(lang.getString("coachingList").split(",")))
        );
        params.put("coachSection", coachingParam(lang));
        params.put("psySection", psyParam(lang));
        params.put("rezSection", resilienceParam(lang));

        return params;
    }

    private void split(MyResourceBundle lang, HashMap params, String key) {
        params.put(key, new ArrayList<>(Arrays.asList(lang.getString(key).split(","))));

    }

    private HashMap coachingParam(MyResourceBundle resourceBundle) {
        HashMap coachSection = new HashMap();
        List coachTitles=new ArrayList<>(Arrays.asList(resourceBundle.getString("titleCoach").split(",")));
        List<String> coaching = new ArrayList<>();
        coaching.add(resourceBundle.getString("bankCoachSection"));
        coaching.add(resourceBundle.getString("businessCoachSection"));
        coaching.add(resourceBundle.getString("executiveCoachSection"));
        coaching.add(resourceBundle.getString("lifeCoachSection"));
        for (int i = 0; i< coachTitles.size();i++) {
            coachSection.put(coachTitles.get(i),coaching.get(i));
        }

        return coachSection;
    }
    
    private HashMap psyParam(MyResourceBundle resourceBundle) {
        HashMap psySection = new HashMap();
        List psyTitles=new ArrayList<>(Arrays.asList(resourceBundle.getString("titlePsy").split(",")));
        List<String> psy = new ArrayList<>();
        psy.add(resourceBundle.getString("whatpsySection"));
        psy.add(resourceBundle.getString("whopsySection"));
        psy.add(resourceBundle.getString("whatkindofpsySection"));
        for (int i = 0; i< psyTitles.size();i++) {
            psySection.put(psyTitles.get(i),psy.get(i));
        }
        return psySection;
    }

    private HashMap resilienceParam(MyResourceBundle myResourceBundle) {
        HashMap rezSection = new HashMap();
        List rezTitles=new ArrayList<>(Arrays.asList(myResourceBundle.getString("titleRez").split(",")));
        List<String> rez = new ArrayList<>();
        rez.add(myResourceBundle.getString("whatrezSection"));
        rez.add(myResourceBundle.getString("whyRezSection"));
        rez.add(myResourceBundle.getString("rezHelpSection"));
        rez.add(myResourceBundle.getString("rezProgSection"));
        for (int i = 0; i< rezTitles.size();i++) {
            rezSection.put(rezTitles.get(i),rez.get(i));
        }
        return rezSection;
    }

}
