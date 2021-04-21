//package com.speedhome.dao;
//
//import com.speedhome.entity.Category;
//import com.speedhome.entity.Property;
//import com.speedhome.entity.Role;
//import com.speedhome.entity.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@ActiveProfiles("test")
//public class PropertyDaoH2 {
//
//    @MockBean
//    private PropertyRepository propertyDao;
//
//    @MockBean
//    private PropertyCriteria propertyCriteria;
//
//
//    Property property=new Property();
//    Role admin = new Role();
//    User user = new User();
//    Category category = new Category();
//
//    @BeforeEach
//    public void init(){
//        property.setId(1);
//        property.setAddress("Akshar Township");
//        property.setCity("Navsari");
//        property.setState("Gujarat");
//        property.setPincode("396445");
//
//        admin.setId(1);
//        admin.setName("ADMIN");
//
//        user.setUsername("user");
//        user.setPassword("$2a$10$ZAUnie28lONHcWRgyfZPzeAodGamNUxcHngOj4nXtG0.FMF1uGVzy");
//        user.setEnabled(true);
//        user.setRoles(Arrays.asList(admin));
//        property.setUser(user);
//
//        category.setId(1);
//        category.setName("BUNGLOW");
//        property.setCategory(category);
//    }
//
//    @Test
//    public void saveProperty(){
//        Property result= propertyDao.save(property);
//        assertNotNull(result);
//        assertEquals(result.getId(),property.getId());
//    }
//
//}
