package org.ais.util;

import java.util.HashMap;
import java.util.Map;

/**
 * This class converts the query parameter into map
 */
public class QueryParamUtil {
    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                }
            }
        }
        return result;
    }
}
