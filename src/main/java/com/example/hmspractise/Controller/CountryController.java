package com.example.hmspractise.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/country")
public class CountryController {
    @PostMapping("/con")
    public ResponseEntity<?>countryd(){
        return new ResponseEntity<>("country data ", HttpStatus.CREATED);
    }
}
