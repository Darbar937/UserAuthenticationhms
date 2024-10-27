package com.example.hmspractise.repository;

import com.example.hmspractise.Model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    Optional<UserData>findByUsername(String username);
    Optional<UserData>findByEmail(String email);
}