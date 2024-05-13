package com.example.demo.dto;

import lombok.Data;

@Data
public class LoginRequest {
    public String username;
    public String password;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
