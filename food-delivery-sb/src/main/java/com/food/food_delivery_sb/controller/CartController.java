package com.food.food_delivery_sb.controller;

import com.food.food_delivery_sb.model.CartRequest;
import com.food.food_delivery_sb.model.CartResponse;
import com.food.food_delivery_sb.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public CartResponse addToCart(@RequestBody CartRequest request){
        String foodId = request.getFoodId();
        if(!StringUtils.hasText(foodId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"foodId not found");
        }
        return cartService.addToCart(request);
    }

    @GetMapping
    public CartResponse getCart(){
        return cartService.getCart();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(){
        cartService.clearCart();
    }

    @PostMapping("/remove")
    public CartResponse removeFromCart(@RequestBody CartRequest request){
        String foodId = request.getFoodId();
        if(!StringUtils.hasText(foodId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"foodId not found");
        }
        return cartService.removeFromCart(request);
    }
}
