package com.food.food_delivery_sb.service;

import com.food.food_delivery_sb.model.UserRequest;
import com.food.food_delivery_sb.model.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRequest request);

    String findByUserId();
}
