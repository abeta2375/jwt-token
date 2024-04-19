package com.airbnb.service.impl;

import com.airbnb.entity.PropertyUser;
import com.airbnb.payload.LoginDto;
import com.airbnb.payload.PropertyUserDto;
import com.airbnb.repository.PropertyUserRepository;
import com.airbnb.service.JWTService;
import com.airbnb.service.UserService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private PropertyUserRepository userRepository;
    private JWTService jwtService;

    public UserServiceImpl(PropertyUserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public PropertyUser addUser(PropertyUserDto userDto) {
        PropertyUser user = new PropertyUser();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUserRole(userDto.getUserRole());
        user.setUsername(userDto.getUsername());
        user.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(10)));

        PropertyUser saved = userRepository.save(user);
        return saved;

    }

    @Override
    public String verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> opUser = userRepository.findByUsername(loginDto.getUsername());

        if (opUser.isPresent()){
            PropertyUser user = opUser.get();

            if(BCrypt.checkpw(loginDto.getPassword(), user.getPassword())){
                return jwtService.generateToken(user);
            }

        }
        return null;
    }
}
