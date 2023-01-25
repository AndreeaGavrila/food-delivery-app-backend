package com.example.fooddelivery.model.dto.user;

import com.example.fooddelivery.enums.Role;
import com.example.fooddelivery.model.ClientUser;
import com.example.fooddelivery.model.dto.OrderDto;
import com.example.fooddelivery.model.dto.ProductDto;
import com.example.fooddelivery.model.dto.ReviewDto;
import com.example.fooddelivery.model.dto.UserAddressDto;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
public class ClientUserDto extends BaseUserDto{

    private List<UserAddressDto> addresses;
    private List<ReviewDto> reviews;
    private List<OrderDto> orders;
    private List<ProductDto> favoriteProducts;

    public static @NotNull ClientUserDto entityToDto(@NotNull ClientUser clientUser) {
        ClientUserDto clientUserDto = new ClientUserDto();
        clientUserDto.setId(clientUserDto.getId());
        clientUserDto.setEmail(clientUser.getEmail());
        clientUserDto.setLastName(clientUser.getLastName());
        clientUserDto.setFirstName(clientUser.getFirstName());
        clientUserDto.setRole(Role.ROLE_CLIENT_USER.toString());
        clientUserDto.setPassword(clientUser.getPassword());

        if(clientUser.getAddresses() != null) {
            clientUserDto.setAddresses(clientUser.getAddresses()
                    .stream().map(UserAddressDto::entityToDto).collect(toList()));
        } else {
            clientUserDto.setAddresses(new ArrayList<>());
        }
        if(clientUser.getReviews() != null) {
            clientUserDto.setReviews(clientUser.getReviews()
                    .stream().map(ReviewDto::entityToDto).collect(toList()));
        } else {
            clientUserDto.setReviews(new ArrayList<>());
        }
        if(clientUser.getOrders() != null) {
            clientUserDto.setOrders(clientUser.getOrders()
                    .stream().map(OrderDto::entityToDto).collect(toList()));
        } else {
            clientUserDto.setOrders(new ArrayList<>());
        }
        if(clientUser.getProducts() != null) {
            clientUserDto.setFavoriteProducts(clientUser.getProducts()
                    .stream().map(ProductDto::entityToDto).collect(toList()));
        } else {
            clientUserDto.setFavoriteProducts(new ArrayList<>());
        }
        return clientUserDto;
    }

}
