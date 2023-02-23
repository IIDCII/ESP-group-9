package com.example.espg9app.ui.StudentMain;

public class Business {
    private String id;
    private String name;
    private int image;

    public Business(String id, String name, int image){
        this.id = id;
        this.name = name;
        this.image = image;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
