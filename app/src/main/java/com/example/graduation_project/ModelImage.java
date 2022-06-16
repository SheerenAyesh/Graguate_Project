package com.example.graduation_project;

public class ModelImage {
    private String id,imageurl,partname,partnumber,model,description,price,username;

    public ModelImage() {
    }

    public ModelImage(String id, String imageurl,String username,String partnumber,String model,String description,String price,String partname) {
        this.id = id;
        this.imageurl = imageurl;
        this.username = username;
        this.partnumber = partnumber;
        this.model = model;
        this.description = description;
        this.price = price;
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

    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}