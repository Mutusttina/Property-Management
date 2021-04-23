package com.speedhome.dao;

import com.speedhome.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class RoleDaoTest {

    @Autowired
    RoleRepository roleDao;

    Role role=null;

    @BeforeEach
    public void init(){
        role=new Role();
        role.setName("ADMIN");
        role.setId(1);
    }

    @Test
    public void addRole(){
        Role result=roleDao.save(role);
        assertNotNull(result);
        assertEquals("ADMIN",result.getName());
    }

    @Test
    public void updateRole(){
        role.setName("USER");
        Role result=roleDao.save(role);
        assertNotNull(result);
        assertEquals(role.getName(),result.getName());
    }

    @Test
    public void findRoleById(){
        roleDao.save(role);
        Role result=roleDao.findById(role.getId()).get();
        assertNotNull(result);
        assertEquals(role.getId(),result.getId());
    }

    @Test
    public void deleteRole(){
        int id=roleDao.save(role).getId();
        roleDao.deleteById(id);
        assertEquals(Optional.empty(), roleDao.findById(id));
    }

}
