package com.trues.util;

import com.trues.config.model.Config;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * User: son.nguyen
 * Date: 12/25/13
 * Time: 10:55 AM
 */
public class TrueLogUtil {

    protected static ObjectMapper mapper = new ObjectMapper();

    private static List<String> defaultExcludes = new ArrayList<String>(1);
    static {
        defaultExcludes.add("hasResult");
        defaultExcludes.add("noResult");
    }
    public static void printLine(Logger log, String sessionId, String text) {
        log.info(String.format(" - %s : === %s ===", sessionId, text));
    }

    public static void printInput(Logger log, String sessionId, Config cf, List<String> excludeFields, Object obj) {

        log.info(String.format(" - %s : ===== Calling: <%s/%s> =====", sessionId, cf.getProcerdure(), cf.getTableName()));
        log.info(String.format(" - %s : === DB Server: %s ===", sessionId, cf.getConnectStr()));
        String json = "";
        try {
            json =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
        }
        //Map<String, Object> mapFields = AISLogUtil.ob2Map(obj);
        log.info(String.format(" - %s : === Input: ===", sessionId));
        if (StringUtils.isNotEmpty(json)) {
            String[] split = json.split("\\n");
            if (excludeFields != null && excludeFields.size() > 0) {
                for (String key : split) {
                    if (!excludeFields.contains(key)) {
                        log.info(String.format(" - %s : <-- %s", sessionId, key));
                    }
                }
            } else {
                for (String key : split) {
                    log.info(String.format(" - %s : <-- %s", sessionId, key));
                }
            }
        }

    }

    public static void printOutput(Logger log, String sessionId, Config cf, List<String> excludeFields, Object obj) {
        //Map<String, Object> mapFields = AISLogUtil.ob2Map(obj);
        String json = "";
        try {
            json =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
        }

        log.info(String.format(" - %s : === Output: <%s>  ===", sessionId, cf.getProcerdure()));
        if (StringUtils.isNotEmpty(json)) {
            String[] split = json.split("\\n");
            for (String key : split) {
                log.info(String.format(" - %s : --> %s", sessionId, key));
            }
        }
    }

    public static void printInsert(Logger log, String sessionId, Config cf, List<String> excludeFields, Object obj) {

        log.info(String.format(" - %s : ===== Calling: <%s/%s> =====", sessionId, cf.getProcerdure(), cf.getTableName()));
        log.info(String.format(" - %s : === DB Server: %s ===", sessionId, cf.getConnectStr()));
        String json = "";
        try {
            json =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
        }
        //Map<String, Object> mapFields = AISLogUtil.ob2Map(obj);
        log.info(String.format(" - %s : === Input: ===", sessionId));
        if (StringUtils.isNotEmpty(json)) {
            String[] split = json.split("\\n");
            if (excludeFields != null && excludeFields.size() > 0) {
                for (String key : split) {
                    if (!excludeFields.contains(key)) {
                        log.info(String.format(" - %s : <-- %s", sessionId, key));
                    }
                }
            } else {
                for (String key : split) {
                    log.info(String.format(" - %s : <-- %s", sessionId, key));
                }
            }
        }

    }

    public static void printInsertMask(Logger log, String sessionId, Config cf, List<String> maskFields, Object obj) {

        log.info(String.format(" - %s : ===== Calling: <%s/%s> =====", sessionId, cf.getProcerdure(), cf.getTableName()));
        log.info(String.format(" - %s : === DB Server: %s ===", sessionId, cf.getConnectStr()));
        String json = "";
        try {
            json =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
        }
        log.info(String.format(" - %s : === Input: ===", sessionId));
        if (StringUtils.isNotEmpty(json)) {
            String[] split = json.split("\\n");
            if (maskFields != null && maskFields.size() > 0) {
                for (String key : split) {
                    if (!contains(maskFields, key)) {
                        log.info(String.format(" - %s : <-- %s", sessionId, key));
                    } else {
                        log.info(String.format(" - %s : <-- %s", sessionId, replace(key)));
                    }
                }
            } else {
                for (String key : split) {
                    log.info(String.format(" - %s : <-- %s", sessionId, key));
                }
            }
        }

    }

    private static String replace(String key) {
        if (StringUtils.isNotEmpty(key)) {
            String[] str = key.split(":");
            if (str.length > 1) {
                return str[0] + " : " + "**********************";
            }
        }
        return key;
    }

    private static boolean contains(List<String> fields, String key) {
        if (StringUtils.isNotEmpty(key)) {
            String str = key.toLowerCase();
            for (String k : fields) {
                if (str.contains(k.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }


}
