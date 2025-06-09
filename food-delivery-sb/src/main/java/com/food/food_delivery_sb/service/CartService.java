package com.food.food_delivery_sb.service;

import com.food.food_delivery_sb.model.CartRequest;
import com.food.food_delivery_sb.model.CartResponse;

public interface CartService {

    CartResponse addToCart(CartRequest request);

    CartResponse getCart();

    void clearCart();

    CartResponse removeFromCart(CartRequest request);
}
