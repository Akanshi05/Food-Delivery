package com.food.food_delivery_sb.service.impl;

import com.food.food_delivery_sb.entity.FoodEntity;
import com.food.food_delivery_sb.exception.ResourceNotFoundException;
import com.food.food_delivery_sb.model.FoodRequest;
import com.food.food_delivery_sb.model.FoodResponse;
import com.food.food_delivery_sb.repository.FoodRepository;
import com.food.food_delivery_sb.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Value("${project.image}")
    private String path;

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileNameWithExtn = fileName.concat(ext);
        String fullPathWithFileName = path.concat(fileNameWithExtn);
        Set<String> allowedExtns = new HashSet<>(List.of(".png", ".jpg", ".jpeg"));
        if(allowedExtns.contains(ext)){
           File folder = new File(path);
           if(!folder.exists()){
              folder.mkdir();
           }

           Files.copy(file.getInputStream(),Paths.get(fullPathWithFileName));
           return fileNameWithExtn;
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"File with this extn not allowed");
        }
    }

    @Override
    public FoodResponse addFood(FoodRequest request, MultipartFile file) throws IOException {
        FoodEntity newFoodEntity = convertToEntity(request);
        String imageName = uploadFile(path,file);
        newFoodEntity.setImageName(imageName);
        newFoodEntity = foodRepository.save(newFoodEntity);
        return convertToResponse(newFoodEntity);
    }

    @Override
    public List<FoodResponse> readFoods() {
        List<FoodEntity> foodEntityList = foodRepository.findAll();
        return foodEntityList.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public FoodResponse readFood(String id) {
        FoodEntity foodEntity = foodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("food not found for this id:"+id));
        return convertToResponse(foodEntity);
    }

    @Override
    public boolean deleteFile(String fileName) throws IOException {
        String fullPath = path+fileName;
        Path path = Paths.get(fullPath);
        Files.delete(path);
        return true;
    }

    @Override
    public void deleteFood(String id) throws IOException {
        FoodResponse response = readFood(id);
        String fileName = response.getImageName();
        foodRepository.deleteById(response.getId());

    }

    private FoodEntity convertToEntity(FoodRequest request){
        return FoodEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .build();
    }

    private FoodResponse convertToResponse(FoodEntity entity){
        return FoodResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .price(entity.getPrice())
                .imageName(entity.getImageName())
                .build();
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path+File.separator+fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
