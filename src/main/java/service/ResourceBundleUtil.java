package service;

import controller.enumeration.AttributeType;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class ResourceBundleUtil {
    private static final String LANGUAGE_RU = "ru";
    private static final String LANGUAGE_EN = "en";
    private static final String COUNTRY_RU = "RU";
    private static final String COUNTRY_EN = "US";
    private static final String RESOURCE_NAME = "textResources";

    private ResourceBundleUtil() {
    }

    /**
     * Gets ResourceBundle depending on HttpServletRequest
     *
     * @param req - request
     * @return ResourceBundle with locale
     */
    public static ResourceBundle getResourceBundle(HttpServletRequest req) {
        Optional<String> lang = Arrays.stream(req.getCookies())
                .filter(c -> AttributeType.LANGUAGE.getValue().equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();

        String language;
        String country;

        if (lang.isPresent()) {
            language = lang.get();
            country = language.equals(LANGUAGE_RU) ? COUNTRY_RU : COUNTRY_EN;
        } else {
            language = LANGUAGE_EN;
            country = COUNTRY_EN;
        }
        Locale currentLocale = new Locale(language, country);
        return ResourceBundle.getBundle(RESOURCE_NAME, currentLocale);
    }
}
