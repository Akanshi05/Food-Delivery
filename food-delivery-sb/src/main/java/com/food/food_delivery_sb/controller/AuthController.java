package com.food.food_delivery_sb.controller;

import com.food.food_delivery_sb.model.AuthenticationRequest;
import com.food.food_delivery_sb.model.AuthenticationResponse;
import com.food.food_delivery_sb.service.impl.AppUserDetailsService;
import com.food.food_delivery_sb.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request){
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
         final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
         final String jwtToken = jwtUtil.generateToken(userDetails);
         return new AuthenticationResponse(request.getEmail(), jwtToken);
    }


}
