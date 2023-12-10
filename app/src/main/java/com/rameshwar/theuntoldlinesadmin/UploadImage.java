package com.rameshwar.theuntoldlinesadmin;

public class UploadImage {

    String imageUrl,key;

    public UploadImage(String downloadUrl) {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UploadImage(String imageUrl, String key) {
        this.imageUrl = imageUrl;
        this.key = key;
    }
}
