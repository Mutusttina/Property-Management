package com.speedhome.dao;

import com.speedhome.dao.RoleRepository;
import com.speedhome.entity.Role;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class RoleDaoTest {

    @Mock
    RoleRepository roleDao;

    Role role=new Role();

    @Before
    public void init(){
        role.setName("ADMIN");
        role.setId(1);
    }

    @org.junit.Test
    public void addRole(){
        Mockito.when(roleDao.save(role)).thenReturn(role);
        Role result=roleDao.save(role);
        assertNotNull(result);
        assertEquals("ADMIN",result.getName());
    }

    @org.junit.Test
    public void updateRole(){
        role.setId(2);
        Mockito.when(roleDao.save(role)).thenReturn(role);
        Role result=roleDao.save(role);
        assertNotNull(result);
        assertEquals(2,result.getId());
    }

    @org.junit.Test
    public void findRoleById(){
        Mockito.when(roleDao.findById(role.getId())).thenReturn(Optional.of(role));
        Role result=roleDao.findById(role.getId()).get();
        assertNotNull(result);
        assertEquals(role.getId(),result.getId());
    }

    @org.junit.Test
    public void deleteRole(){
        Mockito.doNothing().when(roleDao).deleteById(role.getId());
        roleDao.deleteById(role.getId());
    }





}
