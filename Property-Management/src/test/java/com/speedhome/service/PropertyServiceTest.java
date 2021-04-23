package com.speedhome.service;

import com.speedhome.dao.CategoryRepository;
import com.speedhome.dao.PropertyCriteria;
import com.speedhome.dao.PropertyRepository;
import com.speedhome.dao.UserRepository;
import com.speedhome.entity.Category;
import com.speedhome.entity.Property;
import com.speedhome.entity.Role;
import com.speedhome.entity.User;
import com.speedhome.model.PropertyAddOrUpdateRequest;
import com.speedhome.model.PropertySearchRequest;
import com.speedhome.model.PropertySearchResponseWrapper;
import com.speedhome.security.UserUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserUtils.class)
public class PropertyServiceTest {
    @Mock
    PropertyRepository propertyDao;
    @Mock
    PropertyCriteria propertyDaoCriteria;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    PropertyServiceImpl propertyService;

    Property property = null;
    Role admin = null;
    User user = null;
    Category category = null;

    @Before
    public void init() {

       property = new Property();
       admin = new Role();
         user = new User();
         category = new Category();

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
        PowerMockito.mockStatic(UserUtils.class);
        BDDMockito.given(UserUtils.getLoggedInUserName()).willReturn("admin");

        Mockito.when(UserUtils.getLoggedInUserName()).thenReturn("admin");
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(category));
        Mockito.when(userRepository.getUserByUsername(UserUtils.getLoggedInUserName())).thenReturn(user);
        when(propertyDao.save(Mockito.any())).thenReturn(property);
        PowerMockito.verifyStatic(UserUtils.class);
        UserUtils.getLoggedInUserName();

        PropertyAddOrUpdateRequest request = new PropertyAddOrUpdateRequest(property.getAddress(), property.getCategory().getId(), property.getCity(), property.getState(), property.getPincode());
        Property result= propertyService.addProperty(request);
        assertNotNull(result);
        assertEquals(property.getAddress(),result.getAddress());
    }

    @org.junit.Test
    public void updateProperty() {
        Mockito.when(propertyDao.findById(property.getId())).thenReturn(Optional.of(property));
        Mockito.when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(propertyDao.save(property)).thenReturn(property);
        PropertyAddOrUpdateRequest request = new PropertyAddOrUpdateRequest(property.getAddress(), property.getCategory().getId(), property.getCity(), property.getState(), property.getPincode());

        Property result = propertyService.updateProperty(request, property.getId());
        assertEquals(property.getId(),result.getId());
        assertEquals(property.getAddress(),result.getAddress());
    }

    @org.junit.Test
    public void SearchProperty() {
        PropertySearchRequest request = new PropertySearchRequest();
        request.setSearchString("Navsari");
        request.setCatagoryId(2);
        request.setSortBy("city");
        List<Property> list = new ArrayList<>();
        list.add(property);
        when(propertyDaoCriteria.getPropertiesWithSearch(request.getSortBy(), request.getSortOrder(), request.getRecordsPerPage(), request.getSearchString(), request.getCatagoryId(), request.getPageIndex())).thenReturn(list);
        PropertySearchResponseWrapper wrapper = propertyService.searchProperty(request);
        assertNotNull(wrapper);
    }

    @org.junit.Test
    public void approveProperty() {
        property.setApproved(true);
        when(propertyDao.findById(property.getId())).thenReturn(Optional.of(property));
        when(propertyDao.save(property)).thenReturn(property);
       Property result= propertyService.approveProperty(property.getId());
       assertNotNull(result);
    }

}

