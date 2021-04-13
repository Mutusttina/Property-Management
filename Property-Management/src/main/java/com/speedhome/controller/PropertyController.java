package com.speedhome.controller;
import com.speedhome.model.PropertyAddOrUpdateRequest;
import com.speedhome.model.PropertySearchRequest;
import com.speedhome.model.PropertySearchResponse;
import com.speedhome.model.PropertySearchResponseWrapper;
import com.speedhome.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public void add(@RequestBody PropertyAddOrUpdateRequest property){
         propertyService.addProperty(property);
    }

    @PutMapping
    public void update(@RequestBody PropertyAddOrUpdateRequest property, @RequestParam("id") int propertyId){
        System.out.println(propertyId);
        propertyService.updateProperty(property,propertyId);
    }

    @PostMapping("/_approve/{propertyId}")
    public void approve(@PathVariable int propertyId){
        propertyService.approveProperty(propertyId);
    }

    @GetMapping("/hello")
    public String smtg(){
        return "Hello";
    }

    @PostMapping("/_search")
    public PropertySearchResponseWrapper search(@RequestBody PropertySearchRequest request){
       return propertyService.searchProperty(request);
    }




}
