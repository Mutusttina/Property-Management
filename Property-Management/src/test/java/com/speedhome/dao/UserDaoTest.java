package com.speedhome.dao;

import com.speedhome.entity.Role;
import com.speedhome.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class UserDaoTest {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private RoleRepository roleRepository;


    User user=null;
    Role role=null;
    @BeforeEach
    public void init(){
        user=new User();
        role=new Role();

        role.setId(1);
        role.setName("ADMIN");

        user.setUsername("user");
        user.setPassword("123");
        user.setEnabled(true);
        user.getRoles().add(role);

        roleRepository.save(role);
    }


    @Test
    public void saveUser(){
      
        User result=userDao.save(user);
        assertNotNull(result);
        assertEquals(user.getId(),result.getId());
    }

    @Test
    public void updateUser(){
      

       User savedUser= userDao.save(user);
        assertEquals("user",savedUser.getUsername());

        savedUser.setUsername("sagar");
        User updatedUser= userDao.save(savedUser);

        assertNotNull(updatedUser);
        assertEquals(savedUser.getId(),updatedUser.getId());
        assertEquals("sagar",updatedUser.getUsername());
    }

    @Test
    public void findUserByUserName(){
        userDao.deleteAll();
      
        User savedUser= userDao.save(user);
        System.out.println(userDao.findAll().size());
        userDao.findAll().forEach(it-> System.out.println(it.getUsername()));
        User result= userDao.getUserByUsername(savedUser.getUsername());

        assertNotNull(result);
        assertEquals(savedUser.getUsername(),result.getUsername());
    }

    @Test
    public void findUserById(){
      
        int id=userDao.save(user).getId();
        try {
            User result = userDao.findById(id).get();
            assertNotNull(result);
            assertEquals(id,result.getId());
        }catch (NoSuchElementException exception){
            System.out.println("cannot find element");
            exception.printStackTrace();
        }
    }

    @Test
    public void deleteUser(){
      
        int id=userDao.save(user).getId();
        userDao.deleteById(id);
        assertEquals(Optional.empty(),userDao.findById(id));
    }

}
