package com.airbnb.service;

import com.airbnb.entity.PropertyUser;
import com.airbnb.payload.LoginDto;
import com.airbnb.payload.PropertyUserDto;

public interface UserService {

    //add user
    PropertyUser addUser(PropertyUserDto userDto);

    //verify-login
    String verifyLogin(LoginDto loginDto);
}
