package com.speedhome.dao;

import com.speedhome.entity.Category;
import com.speedhome.entity.Property;
import com.speedhome.entity.Role;
import com.speedhome.entity.User;
import com.speedhome.model.PropertySearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PropertyDaoTest {

    @Autowired
    private PropertyRepository propertyDao;

    @Autowired
    private PropertyCriteria propertyCriteria;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    Property property=null;
    Role admin =null;
    User user = null;
    Category category = null;

    @BeforeEach
    public void init(){

        property=new Property();
        admin=new Role();
        user=new User();
        category=new Category();


        property.setId(1);
        property.setAddress("Akshar Township");
        property.setCity("Navsari");
        property.setState("Gujarat");
        property.setPincode("396445");

       // admin.setId(1);
        admin.setName("ADMIN");

        user.setUsername("user");
        user.setPassword("$2a$10$ZAUnie28lONHcWRgyfZPzeAodGamNUxcHngOj4nXtG0.FMF1uGVzy");
        user.setEnabled(true);
        user.setRoles(Arrays.asList(admin));
        property.setUser(user);

        category.setId(1);
        category.setName("BUNGLOW");
        property.setCategory(category);

        categoryRepository.save(category);
        roleRepository.save(admin);
        userRepository.save(user);
    }

    @Test
    public void saveProperty(){

        Property property1=new Property();
        property1.setAddress(property.getAddress());
        property1.setCity(property.getCity());
        property1.setPincode(property.getPincode());
        property1.setState(property.getState());
        property1.setCategory(categoryRepository.findById(category.getId()).get());
        property1.setUser(userRepository.getUserByUsername(user.getUsername()));


        Property result= propertyDao.save(property);
        assertNotNull(result);
        assertEquals(result.getId(),property.getId());
    }

    @Test
    public void updateProperty(){
        property.setApproved(false);
        property.setAddress("updated address");
        Property result= propertyDao.save(property);
        assertNotNull(result);
        assertEquals(property.getId(),result.getId());
        assertEquals(false,result.isApproved());
        assertEquals("updated address",result.getAddress());
    }

    @Test
    public void findPropertyById(){
        propertyDao.save(property);

        Property result= propertyDao.findById(property.getId()).get();

        assertNotNull(result);
        assertEquals(property.getId(),result.getId());
    }

    @Test
    public void searchProperty(){
        propertyDao.save(property);

        PropertySearchRequest request = new PropertySearchRequest();
        request.setSearchString("Navsari");
        request.setCatagoryId(1);
        request.setSortBy("city");

        List<Property> result= propertyCriteria.getPropertiesWithSearch(request.getSortBy(), request.getSortOrder(), request.getRecordsPerPage(), request.getSearchString(), request.getCatagoryId(), request.getPageIndex());
        assertNotEquals(0,result.size());
        assertEquals(property.getAddress(),result.get(0).getAddress());
    }

    @Test
    public void deleteProperty(){
        propertyDao.save(property);
        propertyDao.deleteById(property.getId());
        assertEquals(Optional.empty(),propertyDao.findById(property.getId()));
    }

}
