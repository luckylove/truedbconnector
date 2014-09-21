package com.trues.config.model;

public class Env {

    private String name;

    private GetConfig getConfig;

    public GetConfig getGetConfig() {
        return getConfig;
    }

    public void setGetConfig(GetConfig getConfig) {
        this.getConfig = getConfig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
