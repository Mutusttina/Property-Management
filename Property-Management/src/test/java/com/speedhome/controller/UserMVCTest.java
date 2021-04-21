package com.speedhome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.speedhome.dao.UserRepository;
import com.speedhome.entity.Role;
import com.speedhome.entity.User;
import com.speedhome.model.AddUsersRequest;
import com.speedhome.model.LoginRequest;
import com.speedhome.security.JwtUtil;
import com.speedhome.security.MyEntryPoint;
import com.speedhome.security.UserDetailsServiceImpl;
import com.speedhome.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collection;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MyEntryPoint myEntryPoint;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    Role admin=null;
    User user=null;


    @BeforeEach
    public void init(){

        admin=new Role();
        user=new User();

        admin.setId(1);
        admin.setName("ADMIN");

        user.setUsername("sagar");
        user.setPassword("$2a$10$ZAUnie28lONHcWRgyfZPzeAodGamNUxcHngOj4nXtG0.FMF1uGVzy");
        user.setEnabled(true);
        user.setRoles(Arrays.asList(admin));
    }

    @Test
    public void addUser() throws Exception{

        ObjectMapper mapper = new ObjectMapper();

        AddUsersRequest userInfo=new AddUsersRequest();
        userInfo.setUsername("sagar");
        userInfo.setEnabled(true);
        userInfo.setPassword("123");
        userInfo.setRoleId(1);

        String jsonString = mapper.writeValueAsString(userInfo);

        Mockito.when(userService.addUser(userInfo)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.username", Matchers.is(userInfo.getUsername())));
    }


    @Test
    public void login() throws Exception{

        ObjectMapper mapper = new ObjectMapper();

        LoginRequest request=new LoginRequest();
        request.setUsername("sagar");
        request.setPassword("123");

        String jsonString = mapper.writeValueAsString(request);

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

        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()))).thenReturn(Mockito.any());
        Mockito.when(userDetailsService.loadUserByUsername(user.getUsername())).thenReturn(userDetails);
        Mockito.when(jwtUtil.generateToken(userDetails)).thenReturn("token value");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.jwt", Matchers.is("token value")))
                .andReturn().getResponse().getContentAsString();
    }
}
