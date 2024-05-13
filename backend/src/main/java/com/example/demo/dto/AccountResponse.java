package com.example.demo.dto;

import com.example.demo.entity.Account;
import com.example.demo.entity.User;
import lombok.Data;

@Data
public class AccountResponse {
    public String token;
    public User user;
    public String pasword;
}
