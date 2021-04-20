package com.speedhome.dao;
import com.speedhome.entity.Property;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property,Integer> {
}
