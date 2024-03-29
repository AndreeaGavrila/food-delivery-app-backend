package com.example.fooddelivery.controller;

import com.example.fooddelivery.model.*;
import com.example.fooddelivery.model.dto.LoginResponse;
import com.example.fooddelivery.model.dto.requests.UsernameAndPasswordAuthRequest;
import com.example.fooddelivery.model.dto.user.*;
import com.example.fooddelivery.service.BaseUserService;
import com.example.fooddelivery.service.ClientUserService;
import com.example.fooddelivery.util.JwtUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthenticationController {

    private final BaseUserService baseUserService;
    private final ClientUserService clientUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(BaseUserService baseUserService, ClientUserService clientUserService,
                                    PasswordEncoder passwordEncoder, JwtUtils jwtUtils,
                                    AuthenticationManager authenticationManager) {
        this.baseUserService = baseUserService;
        this.clientUserService = clientUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@NotNull @RequestBody UsernameAndPasswordAuthRequest request) {

        Optional<BaseUserDto> optionalUser = baseUserService.login(request);
        if(optionalUser.isEmpty() ||
                !passwordEncoder.matches(request.getPassword(), optionalUser.get().getPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUser(optionalUser.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ClientUserDto> register(@RequestBody BaseUserDto dto) {
        ClientUser user = clientUserService.registerUser(dto);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(ClientUserDto.entityToDto(user), HttpStatus.OK);
    }

    @GetMapping("/get-info-from-token/{token}")
    public ResponseEntity<BaseUserDto> getInfoFromToken(@PathVariable("token") String token) {
        BaseUserDto result = baseUserService.getInfoFromToken(token);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
