package com.trues.config.model;

import com.trues.util.TrueUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetConfig {
    private List<DataSource> dataSources = new ArrayList<DataSource>();

    //init later
    private Map<String, Config> configMap;

    //this method will init config and database
    public void initConfigMap() {
        configMap = new HashMap<String, Config>(10);
        for (DataSource ds : this.dataSources) {
            javax.sql.DataSource dataSource = TrueUtils.initDataSource(ds);
            JdbcTemplate jdbcTemplate = TrueUtils.initJdbcTemplate(dataSource);
            jdbcTemplate.setResultsMapCaseInsensitive(true);

            for (Config config : ds.getConfigs()) {
                config.setDataSource(dataSource);
                config.setJdbcTemplate(jdbcTemplate);
                config.setConnectStr(ds.getUrl() + "/" + ds.getUsername());
                configMap.put(config.getMethod(), config);
            }
        }


    }

    public Config lookup(String name) {
        return this.configMap.get(name);
    }


    public List<DataSource> getDataSources() {
        return dataSources;
    }

    public void setDataSources(List<DataSource> dataSources) {
        this.dataSources = dataSources;
    }

    public void addDataSource(DataSource dataSource) {
        this.dataSources.add(dataSource);
    }

    public Map<String, Config> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, Config> configMap) {
        this.configMap = configMap;
    }
}
