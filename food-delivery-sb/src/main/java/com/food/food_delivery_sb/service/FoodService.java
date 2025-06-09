package com.food.food_delivery_sb.service;

import com.food.food_delivery_sb.model.FoodRequest;
import com.food.food_delivery_sb.model.FoodResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public interface FoodService {

    String uploadFile(String path,MultipartFile file) throws IOException;
    InputStream getResource(String path,String fileName) throws FileNotFoundException;

    FoodResponse addFood(FoodRequest request,MultipartFile file) throws IOException;

    List<FoodResponse> readFoods();

    FoodResponse readFood(String id);

    boolean deleteFile(String fileName) throws IOException;

    void deleteFood(String id) throws IOException;
}
