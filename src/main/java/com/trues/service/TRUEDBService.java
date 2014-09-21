package com.trues.service;

import com.trues.config.ServiceConfig;
import com.trues.config.model.Env;
import com.trues.config.model.Environments;
import com.trues.service.model.DBObject;
import com.trues.service.model.QEM_ROUTE;
import com.trues.util.TrueUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * User: son.nguyen
 * Date: 10/24/13
 * Time: 9:36 PM
 * This is  main class for init the resource (dbconfig.xml) from local file and init database connection pool...
 * This class is also entry point for calling the services , it provide some static method for you can call everywhere.
 * AISService.GetPackagePrepaid("aa",123);
 */
public class TRUEDBService {
    private static Logger logger = LoggerFactory.getLogger(TRUEDBService.class);
    private static String DEFAULT_CONFIG_PATH = "dbconfig.xml";
    private static ConfigService configService;

    private static boolean serviceStart = false;

    static {
        try {
            initService();
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException("Can not start db service");
        }
    }

    /**
     * for init the service at beginning.
     * @throws Exception
     */
    public static void initService() throws Exception {
        if (serviceStart == false) {
            String path = TrueUtils.getConfigFile(null, DEFAULT_CONFIG_PATH, 0);
            InputStream inputStream = null;
            if (path != null) {
                logger.info("===== Init service ===== " + path);
                inputStream = new FileInputStream(new File(path));
            } else {
                //load config from classpath
                logger.info("===== Init service from Class path =====");
                ClassPathResource cpr = new ClassPathResource(DEFAULT_CONFIG_PATH);
                inputStream = cpr.getInputStream();
            }
            //map xml config to object
            Environments environments = ServiceConfig.parse(inputStream);
            //get active environment
            Env activeEnv = environments.getActiveEnv();
            logger.info("===== Using profile: " + activeEnv.getName());
            //init database connection and convert all config list to map
            activeEnv.getGetConfig().initConfigMap();

            //create service instance
            configService = new ConfigService(activeEnv.getGetConfig());
            serviceStart = true;
        }
    }


    /**
     * collection of static class is called by flow for get config
     */
    public static DBObject<QEM_ROUTE> GetQemRoute(String _sessionId, final String product, final String grading, final String subject) {
        return configService.GetQemRoute(_sessionId, product, grading, subject);
    }



}
