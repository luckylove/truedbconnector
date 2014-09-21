package com.trues.service;

import com.trues.config.model.Config;
import com.trues.config.model.GetConfig;
import com.trues.service.model.DBObject;
import com.trues.service.model.QEM_ROUTE;
import com.trues.util.TrueLogUtil;
import com.trues.util.TrueUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

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
            SimpleJdbcCall simpleJdbcCall1 = new SimpleJdbcCall(cf.getJdbcTemplate()).withoutProcedureColumnMetaDataAccess()
                    .useInParameterNames("IN_PRODUCT")
                    .useInParameterNames("IN_GRADING")
                    .useInParameterNames("IN_SUBJECT")
                    .declareParameters(
                            new SqlParameter("IN_PRODUCT", Types.VARCHAR),
                            new SqlParameter("IN_GRADING", Types.VARCHAR),
                            new SqlParameter("IN_SUBJECT", Types.VARCHAR),
                            new SqlOutParameter("OUT_PRODUCT", Types.VARCHAR),
                            new SqlOutParameter("OUT_GRADING", Types.VARCHAR),
                            new SqlOutParameter("OUT_KEYWORD", Types.VARCHAR),
                            new SqlOutParameter("OUT_PRIORITY", Types.VARCHAR),
                            new SqlOutParameter("OUT_NOTE", Types.VARCHAR),
                            new SqlOutParameter("OUT_HAS_RESULT", Types.VARCHAR))
                    .withProcedureName(cf.getProcerdure());

            SqlParameterSource in = new MapSqlParameterSource().addValue("IN_PRODUCT", product)
                    .addValue("IN_PRODUCT", product)
                    .addValue("IN_GRADING", grading)
                    .addValue("IN_SUBJECT", subject);
            Map<String, Object> execute = simpleJdbcCall1.execute(in);

            QEM_ROUTE ob = new QEM_ROUTE();
            //map to object
            TrueUtils.map2Object(ob, execute);
            if (!ob.isNoResult()) {
                rs.setResult(ob);
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
