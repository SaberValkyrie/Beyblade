package com.example.demo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Notify {
    public String message;
    public int type;
    public Timestamp createAt = new Timestamp(System.currentTimeMillis());

    public Notify(String message, int type) {
        this.message = message;
        this.type = type;
    }
}
