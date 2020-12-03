package com.ugtechie.agribuyer.models;


public class Product {
    private String _id;
    private String name;
    private String description;
    private String price;
    private String productCategory;
    private String productImage;
    private String OwnerId;
    private Boolean approvalStatus;
    private String BuyerId;

    public Product() {
        //needed by Firebase to sterilize data
    }

    public Product(String _id, String name, String description, String price, String productCategory, String productImage, String ownerId, Boolean approvalStatus, String buyerId) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productCategory = productCategory;
        this.productImage = productImage;
        OwnerId = ownerId;
        this.approvalStatus = approvalStatus;
        BuyerId = buyerId;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public Boolean getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getBuyerId() {
        return BuyerId;
    }

    public void setBuyerId(String buyerId) {
        BuyerId = buyerId;
    }
}
