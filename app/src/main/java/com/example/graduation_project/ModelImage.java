package com.example.graduation_project;

public class ModelImage {
    private String id,imageurl,partname;

    public ModelImage() {
    }

    public ModelImage(String id, String imageurl,String partname) {
        this.id = id;
        this.imageurl = imageurl;
        this.partname = partname;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPartname() {
        return partname;
    }
    public void setPartname(String partname) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}