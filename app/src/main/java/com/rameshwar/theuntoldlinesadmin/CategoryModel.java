package com.rameshwar.theuntoldlinesadmin;

public class CategoryModel {

    private  String name;
    private String url;

    public CategoryModel(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }





    public CategoryModel(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
