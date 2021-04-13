package com.speedhome.service;

import com.speedhome.dao.CategoryRepository;
import com.speedhome.dao.PropertyCriteria;
import com.speedhome.dao.PropertyRepository;
import com.speedhome.entity.Property;
import com.speedhome.model.PropertyAddOrUpdateRequest;
import com.speedhome.model.PropertySearchRequest;
import com.speedhome.model.PropertySearchResponse;
import com.speedhome.model.PropertySearchResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyServiceImpl implements  PropertyService {

    @Autowired
    private PropertyRepository dao;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PropertyCriteria propertyCriteria;

    @Override
    public void addProperty(PropertyAddOrUpdateRequest request) {
        Property property=new Property();
        property.setAddress(request.getAddress());
        property.setCity(request.getCity());
        property.setPincode(request.getPincode());
        property.setState(request.getState());
        property.setCategory(categoryRepository.findById(request.getCategoryId()).get());
        dao.save(property);
    }

    @Override
    public void updateProperty(PropertyAddOrUpdateRequest request, int propertyId) {
        Property property=dao.findById(propertyId).get();
        property.setAddress(request.getAddress());
        property.setCity(request.getCity());
        property.setPincode(request.getPincode());
        property.setState(request.getState());
        property.setCategory(categoryRepository.findById(request.getCategoryId()).get());
        dao.save(property);
    }

    @Override
    public PropertySearchResponseWrapper searchProperty(PropertySearchRequest request) {
        List<Property> propertyList=propertyCriteria.getPropertiesWithSearch(request.getSortBy(),request.getSortOrder(),request.getRecordsPerPage(),request.getSearchString(),request.getCatagoryId(),request.getPageIndex());
       List<PropertySearchResponse> responseList=new ArrayList<>();

        for (Property property : propertyList) {
            PropertySearchResponse obj=new PropertySearchResponse(property.getId(),property.getAddress(),property.getCity(),property.getState(),property.getPincode(),property.getCategory().getName());
            responseList.add(obj);
        }
        return new PropertySearchResponseWrapper(responseList);
    }

    @Override
    public void approveProperty(int propertyId) {
        Property property=dao.findById(propertyId).get();
        property.setApproved(true);
        dao.save(property);
    }


}
