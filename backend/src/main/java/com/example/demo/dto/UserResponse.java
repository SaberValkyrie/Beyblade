package com.example.demo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserResponse {
    public String username;
    public String avatar;
    public byte rank;
    public Timestamp createdAt;

    public UserResponse(String username, String avatar, byte rank, Timestamp createdAt) {
        this.username = username;
        this.avatar = avatar;
        this.rank = rank;
        this.createdAt = createdAt;
    }
}
