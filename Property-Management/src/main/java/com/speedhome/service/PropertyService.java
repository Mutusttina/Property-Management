package com.speedhome.service;

import com.speedhome.model.PropertyAddOrUpdateRequest;
import com.speedhome.model.PropertySearchRequest;
import com.speedhome.model.PropertySearchResponse;
import com.speedhome.model.PropertySearchResponseWrapper;

public interface PropertyService {

    public void addProperty(PropertyAddOrUpdateRequest property);

    void updateProperty(PropertyAddOrUpdateRequest property, int propertyId);

    PropertySearchResponseWrapper searchProperty(PropertySearchRequest request);

    void approveProperty(int propertyId);
}
