package com.airbnb.controller;

import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.dto.TokenResponse;
import com.airbnb.entity.PropertyUser;
import com.airbnb.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private PropertyService propertyService;
    public UserController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    //add users
    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto propertyUserDto){
        PropertyUser propertyUser = propertyService.addUser(propertyUserDto);

        if(propertyUser != null){
            return new ResponseEntity<>("Registration is successfully", HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //verify-and-login
    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(@RequestBody LoginDto loginDto){
        String token = propertyService.verifyLogin(loginDto);

        if(token!=null){

            TokenResponse tokenResponse= new TokenResponse();
            tokenResponse.setToken(token);

            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Invalid Credentials!", HttpStatus.UNAUTHORIZED);
    }

    /**/
    //getting user data
    @GetMapping("/profile")
    public ResponseEntity<PropertyUser> getCurrentUserProfile(@AuthenticationPrincipal PropertyUser user){
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
