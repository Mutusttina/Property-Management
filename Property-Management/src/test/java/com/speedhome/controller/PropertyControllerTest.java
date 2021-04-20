package com.speedhome.controller;

import com.speedhome.controller.PropertyController;
import com.speedhome.entity.Category;
import com.speedhome.entity.Property;
import com.speedhome.entity.Role;
import com.speedhome.entity.User;
import com.speedhome.model.PropertyAddOrUpdateRequest;
import com.speedhome.model.PropertySearchRequest;
import com.speedhome.model.PropertySearchResponse;
import com.speedhome.model.PropertySearchResponseWrapper;
import com.speedhome.service.PropertyService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class PropertyControllerTest {

    @InjectMocks
    private PropertyController propertyController;

    @Mock
    private PropertyService propertyService;

    Property property=new Property();
    Role admin = new Role();
    User user = new User();
    Category category = new Category();

    @Before
    public void init(){
        property.setId(1);
        property.setAddress("Akshar Township");
        property.setCity("Navsari");
        property.setState("Gujarat");
        property.setPincode("396445");

        admin.setId(1);
        admin.setName("ADMIN");

        user.setUsername("user");
        user.setPassword("$2a$10$ZAUnie28lONHcWRgyfZPzeAodGamNUxcHngOj4nXtG0.FMF1uGVzy");
        user.setEnabled(true);
        user.setRoles(Arrays.asList(admin));
        property.setUser(user);

        category.setId(1);
        category.setName("BUNGLOW");
        property.setCategory(category);
    }

    @org.junit.Test
    public void addProperty() {
        Mockito.when(propertyService.addProperty(Mockito.any())).thenReturn(property);
        Property result=propertyController.add(Mockito.any());
        assertNotNull(result);
    }

    @org.junit.Test
    public void updateProperty() {
        property.setAddress("Pramukh Township");
        PropertyAddOrUpdateRequest request = new PropertyAddOrUpdateRequest(property.getAddress(), property.getCategory().getId(), property.getCity(), property.getState(), property.getPincode());
        Mockito.when(propertyService.updateProperty(request,property.getId())).thenReturn(property);
        Property result=propertyController.update(request,property.getId());

        assertNotNull(result);
        assertEquals("Pramukh Township",result.getAddress());
    }


    @org.junit.Test
    public void SearchProperty() {
        PropertySearchRequest request = new PropertySearchRequest();
        request.setSearchString("Navsari");
        request.setCatagoryId(2);
        request.setSortBy("city");

        PropertySearchResponse propertySearchResponse=new PropertySearchResponse();
        propertySearchResponse.setAddress(property.getAddress());
        propertySearchResponse.setCategoryName(property.getCategory().getName());
        propertySearchResponse.setCity(property.getCity());
        propertySearchResponse.setPincode(property.getPincode());
        propertySearchResponse.setPropertyId(property.getId());
        propertySearchResponse.setState(property.getState());

        List<PropertySearchResponse> list=Arrays.asList(propertySearchResponse);

        PropertySearchResponseWrapper serviceResult=new PropertySearchResponseWrapper(list);

        Mockito.when(propertyService.searchProperty(request)).thenReturn(serviceResult);

        PropertySearchResponseWrapper controllerResult= propertyController.search(request);

        assertNotNull(controllerResult);
        assertEquals("Akshar Township",controllerResult.getProperties().get(0).getAddress());
        System.out.println(controllerResult.getProperties().get(0).getAddress());
    }

    @org.junit.Test
    public void approveProperty() {
        property.setApproved(true);
        Mockito.when(propertyService.approveProperty(property.getId())).thenReturn(property);

       Property propertyResult= propertyController.approve(property.getId());
       assertNotNull(propertyResult);
       assertEquals(1,property.getId());
       assertEquals("Navsari",property.getCity());
       assertEquals(true,property.isApproved());
    }

}
