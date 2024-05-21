package com.example.demo.dto;

import com.example.demo.entity.BeyBlade;
import lombok.Data;

@Data
public class ItemShop {
    public int stt;
    public String code;
    public BeyBlade beyBlade;
    public int quantity;
    public int price;
    public int day;
}
