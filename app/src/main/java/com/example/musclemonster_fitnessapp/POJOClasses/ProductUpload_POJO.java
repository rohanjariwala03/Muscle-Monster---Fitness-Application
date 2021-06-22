package com.example.musclemonster_fitnessapp.POJOClasses;

public class ProductUpload_POJO
{
    private String  ProductName,ProductWeight,ProductPrice,ProductCat,ProductDesc,ImageUri,FKey,UserKey;
    private String ProdID;

    public ProductUpload_POJO(String prodID, String productName, String productWeight, String productPrice, String productCat, String productDesc, String imageUri, String userKey) {
        ProdID = prodID;
        ProductName = productName;
        ProductWeight = productWeight;
        ProductPrice = productPrice;
        ProductCat = productCat;
        ProductDesc = productDesc;
        ImageUri = imageUri;
        UserKey = userKey;
    }

    public void setProdID(String prodID) {
        ProdID = prodID;
    }

    public String getProdID() {
        return ProdID;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public String getFKey() {
        return FKey;
    }

    public void setFKey(String FKey) {
        this.FKey = FKey;
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
