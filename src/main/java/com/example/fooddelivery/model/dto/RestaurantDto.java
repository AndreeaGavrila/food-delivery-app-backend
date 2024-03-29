package com.example.fooddelivery.model.dto;

import com.example.fooddelivery.model.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
public class RestaurantDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private Double rating;
    private Double deliveryTax;
    private Long restaurantManagerId;
    private List<LocationDto> locations;
    private List<ReviewDto> reviews;

    private List<ProductDto> products;

    public static @NotNull RestaurantDto entityToDto(@NotNull Restaurant restaurant) {
        RestaurantDto dto = new RestaurantDto();
        dto.setName(restaurant.getName());
        dto.setId(restaurant.getId());
        dto.setPhoneNumber(restaurant.getPhoneNumber());
        dto.setRating(restaurant.getRating());
        dto.setDeliveryTax(restaurant.getDeliveryTax());
        dto.setRestaurantManagerId(restaurant.getRestaurantManager().getId());

        if(restaurant.getLocations() != null){
            dto.setLocations(restaurant.getLocations().stream().map(LocationDto::entityToDto).collect(toList()));
        } else {
            dto.setLocations(new ArrayList<>());
        }

        if(restaurant.getReviews() != null) {
            dto.setReviews(restaurant.getReviews().stream().map(ReviewDto::entityToDto).collect(toList()));
        } else {
            dto.setReviews(new ArrayList<>());
        }
        if(restaurant.getProducts() != null){
            dto.setProducts(restaurant.getProducts().stream().map(ProductDto::entityToDto).collect(toList()));
        }else{
            dto.setProducts(new ArrayList<>());
        }

        return dto;
    }
}
