package com.example.fooddelivery.model.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductToFavoritesRequest {

    private Long productId;
    private Long clientUserId;

}
