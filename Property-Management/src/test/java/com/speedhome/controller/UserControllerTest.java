package com.speedhome.controller;

import com.speedhome.entity.Role;
import com.speedhome.entity.User;
import com.speedhome.model.AddUsersRequest;
import com.speedhome.model.LoginRequest;
import com.speedhome.security.JwtUtil;
import com.speedhome.security.UserDetailsServiceImpl;
import com.speedhome.service.UserService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserService userService;

    Role admin=new Role();
    User user=new User();


    @Before
    public void init(){
        admin.setId(1);
        admin.setName("ADMIN");

        user.setUsername("sagar");
        user.setPassword("$2a$10$ZAUnie28lONHcWRgyfZPzeAodGamNUxcHngOj4nXtG0.FMF1uGVzy");
        user.setEnabled(true);
        user.setRoles(Arrays.asList(admin));
    }

    @org.junit.Test
    public void addUser() {
        AddUsersRequest userInfo=new AddUsersRequest();
        userInfo.setUsername("sagar");
        userInfo.setEnabled(true);
        userInfo.setPassword("$2a$10$ZAUnie28lONHcWRgyfZPzeAodGamNUxcHngOj4nXtG0.FMF1uGVzy");
        userInfo.setRoleId(1);


        Mockito.when(userService.addUser(userInfo)).thenReturn(user);
        User result=userController.addUser(userInfo);

        assertNotNull(result);
        assertEquals("sagar",result.getUsername());
        assertEquals("ADMIN",result.getRoles().get(0).getName());
    }

    @org.junit.Test
    public void loginUser() {
        LoginRequest request=new LoginRequest();
        request.setUsername("sagar");
        request.setPassword("123");

        UserDetails userDetails=new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return user.isEnabled();
            }
        };

      //  Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()))).thenReturn(Mockito.any());
        Mockito.when(userDetailsService.loadUserByUsername(user.getUsername())).thenReturn(userDetails);
        Mockito.when(jwtUtil.generateToken(userDetails)).thenReturn("token here");
        ResponseEntity<?> responseEntity= userController.login(request);

    }

}
