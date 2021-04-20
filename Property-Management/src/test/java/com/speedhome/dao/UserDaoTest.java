package com.speedhome.dao;

import com.speedhome.dao.UserRepository;
import com.speedhome.entity.Role;
import com.speedhome.entity.User;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
    
    @Mock
    private UserRepository userDao;

    
    User user=new User();
    Role role=new Role();
    @Before
    public void init(){


        role.setId(1);
        role.setName("ADMIN");

        user.setUsername("user");
        user.setPassword("$2a$10$ZAUnie28lONHcWRgyfZPzeAodGamNUxcHngOj4nXtG0.FMF1uGVzy");
        user.setEnabled(true);
        user.getRoles().add(role);

    }


    @org.junit.Test
    public void saveUser(){
        Mockito.when(userDao.save(user)).thenReturn(user);
        User result=userDao.save(user);

        assertNotNull(result);
       assertEquals(user.getId(),result.getId());

    }

    @org.junit.Test
    public void updateUser(){
       user.setUsername("sagar");
        Mockito.when(userDao.save(user)).thenReturn(user);
        User result= userDao.save(user);

        assertNotNull(result);
        assertEquals(user.getId(),result.getId());
        assertEquals("sagar",result.getUsername());
    }

    @org.junit.Test
    public void findUserById(){
        Mockito.when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        User result= userDao.findById(user.getId()).get();

        assertNotNull(result);
        assertEquals(user.getId(),result.getId());
    }

    @org.junit.Test
    public void deleteUser(){
        Mockito.doNothing().when(userDao).deleteById(user.getId());
        userDao.deleteById(user.getId());
    }





}
