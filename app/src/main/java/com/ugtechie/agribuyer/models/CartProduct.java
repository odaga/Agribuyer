package com.ugtechie.agribuyer.models;

public class CartProduct {

    private String _id;
    private String productId;
    private String name;
    private String description;
    private String price;
    private String productCategory;
    private int quantity;
    private String ownerId;
    private String buyerId;


    public CartProduct(String _id, String productId, String name, String description, String price, String productCategory, int quantity, String ownerId, String buyerId) {
        this._id = _id;
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productCategory = productCategory;
        this.quantity = quantity;
        this.ownerId = ownerId;
        this.buyerId = buyerId;
    }

    public String get_id() {
        return _id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
}
