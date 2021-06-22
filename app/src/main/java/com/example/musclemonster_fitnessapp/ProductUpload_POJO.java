package com.example.musclemonster_fitnessapp;

public class ProductUpload_POJO
{
    private String ProductName,ProductWeight,ProductPrice,ProductCat,ProductDesc,ImageUri;

    public ProductUpload_POJO(String productName, String productWeight, String productPrice, String productCat, String productDesc, String imageUri) {
        ProductName = productName;
        ProductWeight = productWeight;
        ProductPrice = productPrice;
        ProductCat = productCat;
        ProductDesc = productDesc;
        ImageUri = imageUri;
    }

    public ProductUpload_POJO() {
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public void setProductWeight(String productWeight) {
        ProductWeight = productWeight;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public void setProductCat(String productCat) {
        ProductCat = productCat;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getProductWeight() {
        return ProductWeight;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public String getProductCat() {
        return ProductCat;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public String getImageUri() {
        return ImageUri;
    }
}
