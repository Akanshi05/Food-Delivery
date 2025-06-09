package com.food.food_delivery_sb.service.impl;

import com.food.food_delivery_sb.entity.CartEntity;
import com.food.food_delivery_sb.model.CartRequest;
import com.food.food_delivery_sb.model.CartResponse;
import com.food.food_delivery_sb.repository.CartRepository;
import com.food.food_delivery_sb.service.CartService;
import com.food.food_delivery_sb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    @Override
    public CartResponse addToCart(CartRequest request) {
       String loggedInUserId = userService.findByUserId();
       Optional<CartEntity> cartEntityOptional = cartRepository.findByUserId(loggedInUserId);
       CartEntity cart = cartEntityOptional.orElseGet(() -> new CartEntity(loggedInUserId,new HashMap<>()));
       Map<String,Integer> cartItems = cart.getItems();
       cartItems.put(request.getFoodId(), cartItems.getOrDefault(request.getFoodId(),0)+1);
       cart.setItems(cartItems);
       cart = cartRepository.save(cart);
       return convertToResponse(cart);
    }

    @Override
    public CartResponse getCart() {
        String loggedInUserId = userService.findByUserId();
        CartEntity entity = cartRepository.findByUserId(loggedInUserId)
                .orElse(new CartEntity(null,loggedInUserId,new HashMap<>()));
        return convertToResponse(entity);
    }

    @Override
    public void clearCart() {
        String loggedInUserId = userService.findByUserId();
        cartRepository.deleteByUserId(loggedInUserId);
    }

    @Override
    public CartResponse removeFromCart(CartRequest request) {
        String loggedInUserId = userService.findByUserId();
        CartEntity entity = cartRepository.findByUserId(loggedInUserId)
                .orElseThrow(() -> new RuntimeException("Cart is not found"));
        Map<String,Integer> cartItems = entity.getItems();
        if(cartItems.containsKey(request.getFoodId())){
            int currentQty = cartItems.get(request.getFoodId());
            if(currentQty>0){
                cartItems.put(request.getFoodId(), currentQty-1);
            }else{
                cartItems.remove(request.getFoodId());
            }
            entity = cartRepository.save(entity);
        }
        return convertToResponse(entity);
    }

    private CartResponse convertToResponse(CartEntity cartEntity){
        return CartResponse.builder()
                .id(cartEntity.getId())
                .userId(cartEntity.getUserId())
                .items(cartEntity.getItems())
                .build();
    }


}
