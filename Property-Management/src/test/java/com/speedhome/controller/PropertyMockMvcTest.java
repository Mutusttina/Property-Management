package com.speedhome.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.speedhome.dao.UserRepository;
import com.speedhome.entity.Category;
import com.speedhome.entity.Property;
import com.speedhome.entity.Role;
import com.speedhome.entity.User;
import com.speedhome.model.PropertyAddOrUpdateRequest;
import com.speedhome.model.PropertySearchRequest;
import com.speedhome.model.PropertySearchResponse;
import com.speedhome.model.PropertySearchResponseWrapper;
import com.speedhome.security.JwtUtil;
import com.speedhome.security.MyEntryPoint;
import com.speedhome.security.UserDetailsServiceImpl;
import com.speedhome.service.PropertyService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PropertyController.class)
@AutoConfigureMockMvc
public class PropertyMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MyEntryPoint myEntryPoint;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    Property property=null;
    Role admin = null;
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


    @Test
    public void addProperty() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(property);

        Mockito.when(propertyService.addProperty(Mockito.any())).thenReturn(property);

        mockMvc.perform(MockMvcRequestBuilders.post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.city", Matchers.is("Navsari")));
    }

    @Test
    public void updateProperty() throws Exception {
        property.setAddress("Pramukh Township");

        ObjectMapper mapper = new ObjectMapper();

        PropertyAddOrUpdateRequest request = new PropertyAddOrUpdateRequest(property.getAddress(), property.getCategory().getId(), property.getCity(), property.getState(), property.getPincode());

        String jsonString = mapper.writeValueAsString(request);

        Mockito.when(propertyService.updateProperty(Mockito.any(),Mockito.anyInt())).thenReturn(property);

        mockMvc.perform(MockMvcRequestBuilders.put("/properties")
                .param("id", String.valueOf(property.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    public void searchProperty() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        PropertySearchRequest request = new PropertySearchRequest();
        request.setSearchString("Navsari");
        request.setCatagoryId(2);
        request.setSortBy("city");

        String jsonString = mapper.writeValueAsString(request);

        PropertySearchResponse propertySearchResponse=new PropertySearchResponse();
        propertySearchResponse.setAddress(property.getAddress());
        propertySearchResponse.setCategoryName(property.getCategory().getName());
        propertySearchResponse.setCity(property.getCity());
        propertySearchResponse.setPincode(property.getPincode());
        propertySearchResponse.setPropertyId(property.getId());
        propertySearchResponse.setState(property.getState());

        List<PropertySearchResponse> list=Arrays.asList(propertySearchResponse);

        PropertySearchResponseWrapper serviceResult=new PropertySearchResponseWrapper(list);

        Mockito.when(propertyService.searchProperty(Mockito.any())).thenReturn(serviceResult);

        mockMvc.perform(MockMvcRequestBuilders.post("/properties/_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.properties[0].propertyId", Matchers.is(1)));
    }

    @Test
    public void approveProperty() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(property);

        property.setApproved(true);
        Mockito.when(propertyService.approveProperty(property.getId())).thenReturn(property);

        mockMvc.perform(MockMvcRequestBuilders.post("/properties/_approve/{propertyId}","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.city", Matchers.is("Navsari")))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.approved", Matchers.is(true)))
        ;
    }












}
