package com.speedhome.dao;

import com.speedhome.dao.CategoryRepository;
import com.speedhome.entity.Category;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class CategoryDaoTest {

    @Mock
    private CategoryRepository categoryDao;

    Category category=new Category();
    @Before
    public void init(){        
        category.setId(1);
        category.setName("FLAT");
    }

    @org.junit.Test
    public void addCategory(){
        Mockito.when(categoryDao.save(category)).thenReturn(category);
        Category result=categoryDao.save(category);
        assertNotNull(result);
        assertEquals("FLAT",result.getName());
    }

    @org.junit.Test
    public void updateCategory(){
        category.setId(2);
        Mockito.when(categoryDao.save(category)).thenReturn(category);
        Category result=categoryDao.save(category);
        assertNotNull(result);
        assertEquals(2,result.getId());
    }

    @org.junit.Test
    public void findCategoryById(){
        Mockito.when(categoryDao.findById(category.getId())).thenReturn(Optional.of(category));
        Category result=categoryDao.findById(category.getId()).get();
        assertNotNull(result);
        assertEquals(category.getId(),result.getId());
    }

    @org.junit.Test
    public void deleteCategoryById(){
        Mockito.doNothing().when(categoryDao).deleteById(category.getId());
        categoryDao.deleteById(category.getId());
    }



}
