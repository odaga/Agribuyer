package com.ugtechie.agribuyer.models;



public class Product {
    private String productName;
    private String productDescription;
    private String productCategory;
    private String productImageUrl;
    private String productPrice;
    private String productOwnerId;
    private Boolean productApprovalStatus;
    private String productBuyerId;

    public Product() {
        //needed by Firebase to sterilize data
    }

    public Product(String productName, String productDescription, String productCategory, String productImageUrl, String productPrice, String productOwnerId, Boolean productApprovalStatus, String productBuyerId) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productOwnerId = productOwnerId;
        this.productApprovalStatus = productApprovalStatus;
        this.productBuyerId = productBuyerId;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductOwnerId() {
        return productOwnerId;
    }

    public void setProductOwnerId(String productOwnerId) {
        this.productOwnerId = productOwnerId;
    }

    public Boolean getProductApprovalStatus() {
        return productApprovalStatus;
    }

    public void setProductApprovalStatus(Boolean productApprovalStatus) {
        this.productApprovalStatus = productApprovalStatus;
    }

    public String getProductBuyerId() {
        return productBuyerId;
    }

    public void setProductBuyerId(String productBuyerId) {
        this.productBuyerId = productBuyerId;
    }
}
