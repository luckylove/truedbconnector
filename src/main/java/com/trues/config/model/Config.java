package com.trues.config.model;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * User: son.nguyen
 * Date: 10/30/13
 * Time: 10:06 PM
 */
public class Config {

    public static final String DB = "DB";
    public static final String FILE = "FILE";
    public static final String MEMORY = "MEMORY";

    private String method;
    private String procerdure;

    private String config;
    private String connectStr;//just for log
    //refer later
    private javax.sql.DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private String tableName;





    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getProcerdure() {
        return procerdure;
    }

    public void setProcerdure(String procerdure) {
        this.procerdure = procerdure;
    }



    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getConnectStr() {
        return connectStr;
    }

    public void setConnectStr(String connectStr) {
        this.connectStr = connectStr;
    }
}
