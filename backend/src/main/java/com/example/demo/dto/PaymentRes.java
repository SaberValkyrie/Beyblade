package com.example.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentRes implements Serializable {

    public String status;
    public String message;
    public String url;
}
