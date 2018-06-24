package hu.humanskill.page;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class MyResourceBundle {

    private ResourceBundle bundle;
    private String fileEncoding;

    public MyResourceBundle(Locale locale, String fileEncoding){
        this.bundle = ResourceBundle.getBundle("templates/index", locale);
        this.fileEncoding = fileEncoding;
    }

    public MyResourceBundle(Locale locale){
        this(locale, "UTF-8");
    }

    public String getString(String key){
        String value = bundle.getString(key);
        try {
            return new String(value.getBytes("ISO-8859-1"), fileEncoding);
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }

    public Set<String> keySet() {
        return bundle.keySet();
    }
}