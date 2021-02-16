package by.dubrovskaya.controller.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

/**
 * XSS wrapper - encode values
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {
    /**
     * Compiles regex for possible attacks
     */
    private static Pattern[] patterns = new Pattern[]{
            // Avoid anything between script tags
            Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            // Avoid anything in a src='...' type of expression
            Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Remove any lonesome <script ...> tag
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Avoid eval(...) expressions
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Avoid expression(...) expressions
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Avoid javascript:... expressions
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            // Avoid vbscript:... expressions
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
            // Avoid onload= expressions
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
    };

    /**
     * Class constructor
     *
     * @param request
     */
    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * Gets parameter values and encodes them
     *
     * @param name
     * @return encoded values or null if parameter values were null
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null) {
            return null;
        }
        int length = parameterValues.length;
        String[] encodedValues = new String[length];
        for (int i = 0; i < length; i++) {
            encodedValues[i] = unscrewXSSAttack(parameterValues[i]);
        }
        return encodedValues;
    }

    /**
     * Gets parameter and encodes it
     *
     * @param parameter
     * @return encoded parameter
     */
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return unscrewXSSAttack(value);
    }

    /**
     * Gets header and encodes it
     *
     * @param name
     * @return encoded header
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return unscrewXSSAttack(value);
    }

    /**
     * Removes all sections that match a pattern with possible attack
     *
     * @param value
     * @return correct and encoded value
     */
    private String unscrewXSSAttack(String value) {
        if (value != null) {
            // Avoids null characters
            value = value.replaceAll("\0", "");

            // Removes all sections that match a pattern
            for (Pattern scriptPattern : patterns) {
                value = scriptPattern.matcher(value).replaceAll("");
            }
        }
        return value;
    }
}
