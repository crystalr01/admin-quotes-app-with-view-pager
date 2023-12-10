package com.rameshwar.theuntoldlinesadmin;

public class UploadData {


    String url,key;

    public UploadData(String downloadUrl) {
    }

    public UploadData(String url, String key) {
        this.url = url;
        this.key = key;
    }

    public String getImage() {
        return url;
    }

    public void setImage(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
