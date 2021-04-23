package com.speedhome.dao;

import com.speedhome.entity.Category;
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
public class CategoryDaoTest {

    @Autowired
    CategoryRepository categoryRepository;

    Category category=null;

    @BeforeEach
    public void init(){
        category=new Category();
        category.setId(1);
        category.setName("FLAT");
    }

    @Test
    public void addCategory(){
        Category result=categoryRepository.save(category);
        assertNotNull(result);
        System.out.println(result.getName());
        assertEquals("FLAT",result.getName());
    }

    @Test
    public void updateCategory(){
        category.setId(2);
        Category result=categoryRepository.save(category);
        assertNotNull(result);
        assertEquals(2,result.getId());
    }

    @Test
    public void findCategoryById(){
        category=new Category();
        category.setName("BUNGLOW");

        Category newCategory=categoryRepository.save(category);
        assertNotNull(newCategory);
        assertEquals(category.getName(),newCategory.getName());
    }

    @Test
    public void deleteCategoryById(){
        categoryRepository.save(category);
        categoryRepository.deleteById(category.getId());
        assertEquals(Optional.empty(),categoryRepository.findById(category.getId()));
    }

}
