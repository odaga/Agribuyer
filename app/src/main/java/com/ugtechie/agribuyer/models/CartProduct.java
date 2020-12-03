package com.ugtechie.agribuyer.models;

public class CartProduct {

    private String _id;
    private String name;
    private String price;
    private String productCategory;
    private String quantity;
    private String ownerId;
    private String buyerId;


    public CartProduct() {
        //Needed by firebase to Sterialise data
    }

    public CartProduct(String _id, String name, String price, String productCategory, String quantity, String ownerId, String buyerId) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.productCategory = productCategory;
        this.quantity = quantity;
        this.ownerId = ownerId;
        this.buyerId = buyerId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
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
