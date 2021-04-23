package com.speedhome.service;


import com.speedhome.dao.RoleRepository;
import com.speedhome.dao.UserRepository;
import com.speedhome.entity.Role;
import com.speedhome.entity.User;
import com.speedhome.model.AddUsersRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
@InjectMocks
UserServiceImpl userService;

    @Mock
    UserRepository dao;
    @Mock
    PasswordEncoder pwdEncoder;
    @Mock
    RoleRepository roleRepository;

    User user=null;
    Role role=null;

    @Before
    public void init(){
        role=new Role();
        role.setName("ADMIN");
        role.setId(1);

        user=new User();
        user.setId(1);
        user.setEnabled(true);
        user.setPassword("123");
        user.getRoles().add(role);
        user.setUsername("sagar");
    }

    @Test
    public void addUser() {
        AddUsersRequest request=new AddUsersRequest();
        request.setUsername(user.getUsername());
        request.setEnabled(user.isEnabled());
        request.setPassword(user.getPassword());
        request.setRoleId(user.getRoles().get(0).getId());

        Mockito.when(roleRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(role));
        Mockito.when(dao.save(Mockito.any())).thenReturn(user);
        User result=userService.addUser(request);
        assertNotNull(result);
        assertEquals(user.getUsername(),result.getUsername());
    }
    
}
