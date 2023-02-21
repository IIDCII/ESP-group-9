package com.example.espg9app.ui.StudentMain;

public class Business {
    private String id;
    private String name;
    private int image;
    private String tags;

    private float rating;

    public Business(String id, String name, int image, String tags, float rating){
        this.id = id;
        this.name = name;
        this.image = image;
        this.tags = tags;
        this.rating = rating;
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
    public String getTags() {return tags;}

    public void setTags(String tags) {
        this.tags = tags;
    }
    public float getRating() {return rating;}

    public void setTags(float rating) {
        this.rating = rating;
    }
}
