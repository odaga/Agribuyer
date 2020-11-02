package com.ugtechie.agribuyer.models;

public class CartProduct {

    private String productName, productId, ProductQuantity, buyerId, productImage, productPrice;

    public CartProduct() {
        //Needed by firebase to Sterialise data
    }

    public CartProduct(String productName, String productId, String productQuantity, String buyerId, String productImage, String productPrice) {
        this.productName = productName;
        this.productId = productId;
        ProductQuantity = productQuantity;
        this.buyerId = buyerId;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        ProductQuantity = productQuantity;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
