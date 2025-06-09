package com.food.food_delivery_sb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.food_delivery_sb.model.FoodRequest;
import com.food.food_delivery_sb.model.FoodResponse;
import com.food.food_delivery_sb.service.FoodService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin("*")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Value("${project.image}")
    private String path;

    //only admin can use this endpoint
    @PostMapping
    public ResponseEntity<FoodResponse> addFood(@RequestPart("food") String foodString,
                                @RequestPart("file") MultipartFile file) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        FoodRequest request = null;
        try{
           request = objectMapper.readValue(foodString,FoodRequest.class);
        }catch(JsonProcessingException ex){
           return ResponseEntity.badRequest().build();
        }

        FoodResponse response = foodService.addFood(request,file);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<FoodResponse>> readFoods(){
        List<FoodResponse> foodResponses = foodService.readFoods();
        return new ResponseEntity<>(foodResponses,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponse> readFood(@PathVariable String id){
        FoodResponse response = foodService.readFood(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //only admin can access this endpoint
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFood(@PathVariable String id) throws IOException {
        foodService.deleteFood(id);
    }


    @GetMapping(value = "image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName
    , HttpServletResponse response) throws IOException {
        InputStream resource = this.foodService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
