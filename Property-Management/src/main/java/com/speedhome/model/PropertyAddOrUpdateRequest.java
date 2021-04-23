package com.speedhome.model;
public class PropertyAddOrUpdateRequest {
    private String  address;
    private int categoryId;
    private String city;
    private String state;
    private String pincode;

    public PropertyAddOrUpdateRequest(String address, int categoryId, String city, String state, String pincode) {
        this.address = address;
        this.categoryId = categoryId;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
