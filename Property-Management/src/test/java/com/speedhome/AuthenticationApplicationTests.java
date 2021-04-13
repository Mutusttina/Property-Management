package com.speedhome;


import com.speedhome.dao.*;
import com.speedhome.entity.Category;
import com.speedhome.entity.Property;
import com.speedhome.entity.Role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthenticationApplicationTests {

	@Mock
	RoleRepository roleDao;
	@Mock
	UserRepository userDao;
	@Mock
	PropertyRepository propertyDao;
	@Mock
	private PropertyCriteria propertyCriteria;

	@Test
	void addProperty() {
		Property property=new Property();
		property.setId(1);
		property.setAddress("Akshar Township");
		property.setCity("Navsari");
		property.setState("Gujarat");
		property.setPincode("396445");

		Category category=new Category();
		category.setId(1);
		category.setName("BUNGLOW");

		property.setCategory(category);

		when(propertyDao.save(Mockito.any())).thenReturn(property);

	}

	@Test
	void updateProperty() {
		Property property=new Property();
		property.setId(1);
		property.setAddress("Pramukh Township");
		property.setCity("Surat");
		property.setState("Gujarat");
		property.setPincode("396450");

		Category category=new Category();
		category.setId(1);
		category.setName("FLAT");

		property.setCategory(category);

		when(propertyDao.save(Mockito.any())).thenReturn(property);
	}

	@Test
	public void testDatabaseData() {
		System.out.println("Testing database data");
		List<Role> roles=roleDao.findAll();
		int roleSize=roles.size();
		assertEquals(roleSize,3);
		assertEquals("admin",userDao.getUserByUsername("ADMIN").getUsername());
		assertEquals("ADMIN",userDao.getUserByUsername("ADMIN").getRoles().get(0).getName());
	}



}
