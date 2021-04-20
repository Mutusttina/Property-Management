package com.speedhome.dao;

import com.speedhome.dao.PropertyCriteria;
import com.speedhome.dao.PropertyRepository;
import com.speedhome.entity.Category;
import com.speedhome.entity.Property;
import com.speedhome.entity.Role;
import com.speedhome.entity.User;
import com.speedhome.model.PropertySearchRequest;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertyDaoTest {

    @Mock
    private PropertyRepository propertyDao;

    @Mock
    private PropertyCriteria propertyCriteria;


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
    public void saveProperty(){
        Mockito.when(propertyDao.save(property)).thenReturn(property);
        Property result= propertyDao.save(property);
        assertNotNull(result);
        assertEquals(result.getId(),property.getId());
    }

    @org.junit.Test
    public void updateProperty(){
        property.setApproved(false);
        property.setAddress("updated address");
        Mockito.when(propertyDao.save(property)).thenReturn(property);
        Property result= propertyDao.save(property);

        assertNotNull(result);
        assertEquals(false,result.isApproved());
        assertEquals("updated address",result.getAddress());
    }

    @org.junit.Test
    public void findPropertyById(){
        Mockito.when(propertyDao.findById(property.getId())).thenReturn(Optional.of(property));
        Property result= propertyDao.findById(property.getId()).get();

        assertNotNull(result);
        assertEquals(property.getId(),result.getId());
    }

    @org.junit.Test
    public void searchProperty(){

        PropertySearchRequest request = new PropertySearchRequest();
        request.setSearchString("Navsari");
        request.setCatagoryId(1);
        request.setSortBy("city");

        List<Property> list= Arrays.asList(property);

        when(propertyCriteria.getPropertiesWithSearch(request.getSortBy(), request.getSortOrder(), request.getRecordsPerPage(), request.getSearchString(), request.getCatagoryId(), request.getPageIndex())).thenReturn(list);

        List<Property> result= propertyCriteria.getPropertiesWithSearch(request.getSortBy(), request.getSortOrder(), request.getRecordsPerPage(), request.getSearchString(), request.getCatagoryId(), request.getPageIndex());

        assertEquals(list.size(),result.size());
    }

    @org.junit.Test
    public void deleteProperty(){
        Mockito.doNothing().when(propertyDao).deleteById(property.getId());
        propertyDao.deleteById(property.getId());
    }
}
