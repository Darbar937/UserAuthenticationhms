package com.example.hmspractise.Controller;

import com.example.hmspractise.Model.UserData;
import com.example.hmspractise.payload.LoginDto;
import com.example.hmspractise.payload.TokenDto;
import com.example.hmspractise.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authSer;

    @PostMapping("/signUser")
    public ResponseEntity<?>saveUser(@RequestBody UserData userData){
        return new ResponseEntity<>(authSer.saveSignData(userData,"ROLE_USER"), HttpStatus.CREATED);
    }
    @PostMapping("/signOwner")
    public ResponseEntity<?>saveOwner(@RequestBody UserData userData){
        return new ResponseEntity<>(authSer.saveSignData(userData,"ROLE_OWNER"), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<?>loginnn(@RequestBody LoginDto loginDto){
       String token=authSer.loginn(loginDto);
        if(token!=null){
            TokenDto tokenDto=new TokenDto();
            tokenDto.setTokenName(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto,HttpStatus.OK);
        }
        return null;
    }

}
