package com.food.food_delivery_sb.service.impl;

import com.food.food_delivery_sb.entity.UserEntity;
import com.food.food_delivery_sb.model.UserRequest;
import com.food.food_delivery_sb.model.UserResponse;
import com.food.food_delivery_sb.repository.UserRepository;
import com.food.food_delivery_sb.service.AuthenticationFacade;
import com.food.food_delivery_sb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationFacade authenticationFacade;
    @Override
    public UserResponse registerUser(UserRequest request) {
        UserEntity newUser = convertToEntity(request);
        newUser = userRepository.save(newUser);
        return convertToResponse(newUser);
    }

    @Override
    public String findByUserId() {
        String loggedInUserEmail = authenticationFacade.getAuthentication().getName();
        UserEntity loggedInuser = userRepository.findByEmail(loggedInUserEmail).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return loggedInuser.getId();
    }

    private UserEntity convertToEntity(UserRequest request){
        return UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();
    }

    private UserResponse convertToResponse(UserEntity registeredUser){
         return UserResponse.builder()
                 .id(registeredUser.getId())
                 .name(registeredUser.getName())
                 .email(registeredUser.getEmail())
                 .build();
    }
}
