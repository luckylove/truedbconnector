package com.trues.service;

import com.trues.config.model.Config;
import com.trues.config.model.GetConfig;
import com.trues.service.model.DBObject;
import com.trues.service.model.QEM_ROUTE;
import com.trues.util.BeanPropertyRowMapperCustom;
import com.trues.util.TrueLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;

/**
 * User: son.nguyen
 * Date: 10/27/13
 * Time: 9:16 AM
 */
public class ConfigService {

    private Logger logger = LoggerFactory.getLogger(ConfigService.class);

    private GetConfig config;
   // private ExecutorService executor;

    public ConfigService(GetConfig config) {
        this.config = config;
        //new dynamic thread pool
        //executor = Executors.newCachedThreadPool();
    }

    public DBObject<QEM_ROUTE> GetQemRoute(String _sessionId, final String product, final String grading, final String subject) {
        String methodName = "GetQemRoute";
        final Config cf = config.lookup(methodName);
        DBObject<QEM_ROUTE> rs = new DBObject<QEM_ROUTE>();
        if (cf != null) {
            TrueLogUtil.printInput(logger, _sessionId, cf, null, new HashMap<String, Object>() {{
                put("product", product);
                put("grading", grading);
                put("subject", subject);
            }});
            Object[] values = {product, grading, subject, subject};

            int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};

            JdbcTemplate jdbcTemplate = cf.getJdbcTemplate();
            List<QEM_ROUTE> objects = jdbcTemplate.query(cf.getConfig(), values, types, new BeanPropertyRowMapperCustom<QEM_ROUTE>(QEM_ROUTE.class));
            if (!objects.isEmpty()) {
                rs.setResult(objects.get(0));
            }

            //as not found anything
            TrueLogUtil.printOutput(logger, _sessionId, cf, null, rs);
            return rs;
        } else {
            rs.setErrorCode(2);
            rs.setErrorMsg(_sessionId + " : " + "Can not get config for method: " + methodName);
            logger.error(_sessionId + " : " + "Can not get config for method: " + methodName);
            TrueLogUtil.printOutput(logger, _sessionId, cf, null, rs);
            return rs;
        }
    }

}
