package com.example.musclemonster_fitnessapp.POJOClasses;

public class CouponPOJO {

    private String UserKey,Discount,CreateDate,ExpiryDate,Code,SKey;

    public CouponPOJO() {
    }

    public CouponPOJO(String userKey, String discount, String createDate, String expiryDate, String code) {
        UserKey = userKey;
        Discount = discount;
        CreateDate = createDate;
        ExpiryDate = expiryDate;
        Code = code;
    }

    public String getSKey() {
        return SKey;
    }

    public void setSKey(String SKey) {
        this.SKey = SKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getUserKey() {
        return UserKey;
    }

    public String getDiscount() {
        return Discount;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public String getCode() {
        return Code;
    }
}
