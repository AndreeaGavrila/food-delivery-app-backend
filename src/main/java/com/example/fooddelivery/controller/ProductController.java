package com.example.fooddelivery.controller;

import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.dto.CategoryDto;
import com.example.fooddelivery.model.dto.ProductDto;
import com.example.fooddelivery.model.dto.requests.AddOrderProductRequest;
import com.example.fooddelivery.model.dto.requests.AddProductToFavoritesRequest;
import com.example.fooddelivery.model.dto.user.ClientUserDto;
import com.example.fooddelivery.service.CategoryService;
import com.example.fooddelivery.service.ClientUserService;
import com.example.fooddelivery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ClientUserService clientUserService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, ClientUserService clientUserService, CategoryService categoryService) {
        this.productService = productService;
        this.clientUserService = clientUserService;
        this.categoryService = categoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto){
        return ResponseEntity.of(Optional.of(productService.saveProduct(productDto)));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable("id") Long id){
        Optional<Product> optionalProduct = productService.findProductById(id);
        if(optionalProduct.isPresent()){
            ProductDto productDto = ProductDto.entityToDto(optionalProduct.get());
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/get-all-by-restaurantId/{restaurantId}")
    public ResponseEntity<List<ProductDto>> getAllProductsByRestaurantId(@PathVariable("restaurantId") Long restaurantId){
        List<ProductDto> result = productService.getAllProductsByRestaurantId(restaurantId);
        if(result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") Long id){
        boolean result = productService.deleteProduct(id);
        if(result){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/change-availability/{id}")
    public ResponseEntity<ProductDto> changeProductAvailability(@PathVariable("id") Long id){
        ProductDto result = productService.changeProductAvailability(id);
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id,
                                                    @RequestBody ProductDto dto){
        ProductDto result = productService.updateProduct(id, dto);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @PutMapping("/add-orders")
//    public ResponseEntity<ProductDto> addOrderProduct(@RequestBody AddOrderProductRequest addOrderProductRequest){
//        ProductDto result = productService.addOrderProduct(addOrderProductRequest.getOrderId(),
//                addOrderProductRequest.getOrderProductId(), addOrderProductRequest.getQuantity());
//        if(result == null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(result, HttpStatus.OK);
//
//    }

    @PostMapping("/add-product-to-client-favorites")
    public ResponseEntity<ClientUserDto> addProductToFavorite(@RequestBody AddProductToFavoritesRequest request) {
        ClientUserDto result = clientUserService.addProductToFavorite(request);
        if(result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/categories/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> result = categoryService.getAllCategories();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
