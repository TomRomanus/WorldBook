package com.tomward.worldbook;

public enum PropertiesSingleton {
    THE_INSTANCE;
    private String key = "";
    private String userName = "";
    private String countryName = "";

    private PropertiesSingleton() {}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
