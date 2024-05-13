package com.example.demo.dto;

import com.example.demo.entity.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MessageRespone {
    public String username;
    public String newMessage;
    public String avatar;
    public boolean status;
    public Timestamp timestamp;

    public MessageRespone(String username, String newMessage, String avatar, boolean s) {
        this.username = username;
        this.newMessage = newMessage;
        this.avatar = avatar;
        this.status = s;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
}
