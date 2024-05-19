package com.example.demo.dto;

import com.example.demo.entity.BeyBlade;
import com.example.demo.entity.Items;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Player {
    public String user;
    public String avatar;
    public ArrayList<Items> items = new ArrayList<>();

}
