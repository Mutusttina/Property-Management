package com.speedhome.model;

import java.io.Serializable;

public class PropertySearchResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private int propertyId;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String categoryName;

    public PropertySearchResponse(int propertyId, String address, String city, String state, String pincode, String categoryName) {
        this.propertyId = propertyId;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.categoryName = categoryName;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public PropertySearchResponse() {
        super();
    }

}
