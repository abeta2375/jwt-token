package com.airbnb.controller;

import com.airbnb.entity.PropertyUser;
import com.airbnb.payload.LoginDto;
import com.airbnb.payload.PropertyUserDto;
import com.airbnb.payload.TokenResponse;
import com.airbnb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //add-user
    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto userDto){

        PropertyUser user = userService.addUser(userDto);

        if(user != null){
            return new ResponseEntity<>("Registration Successful.", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //verify-login
    @PostMapping("/login")
    public ResponseEntity<?> verifyLogin(@RequestBody LoginDto loginDto){
        String token = userService.verifyLogin(loginDto);

        if(token!=null){
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(token);
            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        }

        else
            return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
    }
}
