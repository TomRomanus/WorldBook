package com.tomward.worldbook.CountyInfo;

public class Upload {
    public String name;
    public String url;

    public Upload() {
        //Default constructor required
    }

    public Upload(String name, String url) {
        this.name = name;
        this.url= url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
