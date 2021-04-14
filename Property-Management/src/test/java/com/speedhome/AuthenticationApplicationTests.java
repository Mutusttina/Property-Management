package com.speedhome;


import com.speedhome.dao.PropertyRepository;
import com.speedhome.dao.RoleRepository;
import com.speedhome.dao.UserRepository;
import com.speedhome.entity.Category;
import com.speedhome.entity.Property;
import com.speedhome.entity.Role;
import com.speedhome.entity.User;
import com.speedhome.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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
	public void testRoleTable() {
		Role admin=new Role();
		admin.setId(1);
		admin.setName("ADMIN");
		Role landlord=new Role();
		admin.setId(1);
		admin.setName("LANDLORD");
		Role user=new Role();
		admin.setId(1);
		admin.setName("USER");

		List<Role> roles=new ArrayList<>();
		roles.add(admin);
		roles.add(landlord);
		roles.add(user);
		when(roleDao.findAll()).thenReturn(roles);
	}

	@Test
	public void testUserTable(){
		User user=new User();
		user.setUsername("admin");
		user.setPassword("$2a$10$ZAUnie28lONHcWRgyfZPzeAodGamNUxcHngOj4nXtG0.FMF1uGVzy");
		user.setEnabled(true);
		user.setId(1);
		List<User> users=userDao.findAll();
		users.add(user);
		when(userDao.findAll()).thenReturn(users);
	}



}
