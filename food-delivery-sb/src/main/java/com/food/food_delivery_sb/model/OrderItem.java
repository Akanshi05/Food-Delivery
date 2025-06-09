package com.food.food_delivery_sb.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {

    private String foodId;
    private int quantity;
    private double price;
    private String category;
    private String imageName;
    private String description;
    private String name;
}
