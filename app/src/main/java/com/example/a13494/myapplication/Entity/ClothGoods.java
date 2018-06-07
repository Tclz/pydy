package com.example.a13494.myapplication.Entity;

public class ClothGoods {

    private String name;
    private double price;
    private String sales;
    private String loc;
    private String fee;
    private String purchaseLink;
    private int imageId;

    public ClothGoods(String name,double price,String sales,String loc,String fee,int imageId,String purchaseLink)
    {
        this.name=name;
        this.price=price;
        this.sales=sales;
        this.loc=loc;
        this.fee=fee;
        this.imageId=imageId;
        this.purchaseLink=purchaseLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getPurchaseLink() {
        return purchaseLink;
    }

    public void setPurchaseLink(String purchaseLink) {
        this.purchaseLink = purchaseLink;
    }
}
