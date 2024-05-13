package com.example.demo.dto;

import com.example.demo.entity.Token;
import lombok.Data;

@Data
public class ResponseOpject {

    public String status;
    public ResponseOpject(){}
    public String message;
    public Object data;

    public ResponseOpject(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;

    }
}
