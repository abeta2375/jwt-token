package com.airbnb.service;

import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyService {
    private PropertyUserRepository userRepository;
    private JWTService jwtService;
    public PropertyService(PropertyUserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    //add user
    public PropertyUser addUser(PropertyUserDto propertyUserDto){
        PropertyUser user = new PropertyUser();
        user.setId(propertyUserDto.getId());
        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setEmail(propertyUserDto.getEmail());
        user.setUsername(propertyUserDto.getUsername());
//        user.setPassword(new BCryptPasswordEncoder().encode(propertyUserDto.getPassword()));
        user.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(), BCrypt.gensalt(10)));
        user.setUserRole(propertyUserDto.getUserRole());

        PropertyUser saved = userRepository.save(user);
        return saved;
    }

    //verify-and-login
    public String verifyLogin(LoginDto loginDto){
        Optional<PropertyUser> opUser = userRepository.findByUsername(loginDto.getUsername());

        if(opUser.isPresent()){
            PropertyUser propertyUser = opUser.get();

            if (BCrypt.checkpw(loginDto.getPassword(), propertyUser.getPassword())){
                return jwtService.generateToken(propertyUser);
            }
        }
        return null;
    }
}
