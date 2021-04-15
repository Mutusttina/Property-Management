package com.speedhome.controller;
import com.speedhome.entity.Property;
import com.speedhome.model.PropertyAddOrUpdateRequest;
import com.speedhome.model.PropertySearchRequest;
import com.speedhome.model.PropertySearchResponse;
import com.speedhome.model.PropertySearchResponseWrapper;
import com.speedhome.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public Property add(@RequestBody PropertyAddOrUpdateRequest property){
        return propertyService.addProperty(property);
    }

    @PutMapping
    public Property update(@RequestBody PropertyAddOrUpdateRequest property, @RequestParam("id") int propertyId){
        return propertyService.updateProperty(property,propertyId);
    }

    @PostMapping("/_approve/{propertyId}")
    public void approve(@PathVariable int propertyId){
        propertyService.approveProperty(propertyId);
    }


    @PostMapping("/_search")
    public PropertySearchResponseWrapper search(@RequestBody PropertySearchRequest request){
       return propertyService.searchProperty(request);
    }




}
