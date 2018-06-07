package com.example.a13494.myapplication.View;

public class PopupItem {
    private String text;
    private int imageId;

    public PopupItem(int imageId,String text)
    {
        this.imageId = imageId;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
