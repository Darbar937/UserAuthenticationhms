package com.example.hmspractise.service;

import com.example.hmspractise.Model.UserData;
import com.example.hmspractise.payload.LoginDto;
import com.example.hmspractise.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserDataRepository useRep;
    @Autowired
    private JWTService jwtService;

    public Object saveSignData(UserData userData, String roleOwner) {
        Optional<UserData>opUsername=useRep.findByUsername(userData.getUsername());
        Optional<UserData>opUseremail=useRep.findByEmail(userData.getEmail());
        if(opUsername.isPresent()){
            return new ResponseEntity<>("username is already present", HttpStatus.BAD_REQUEST);
        }
        else if(opUseremail.isPresent()){
            return new ResponseEntity<>("email is already present", HttpStatus.BAD_REQUEST);
        }
        else{
            String encryptPass= BCrypt.hashpw(userData.getPassword(),BCrypt.gensalt(7));
            userData.setPassword(encryptPass);
            userData.setRole(roleOwner);
            return new ResponseEntity<>(useRep.save(userData),HttpStatus.CREATED);
        }
    }

    public String loginn(LoginDto loginDto) {
        Optional<UserData>opUsername=useRep.findByUsername(loginDto.getUsername());
        if(opUsername.isPresent()){
            UserData usd= opUsername.get();
            if(BCrypt.checkpw(loginDto.getPassword(),usd.getPassword())){
                String tokenn=jwtService.generatedToken(usd.getUsername());
                return tokenn;
            }
            return null;
        }
        return null;
    }
}
