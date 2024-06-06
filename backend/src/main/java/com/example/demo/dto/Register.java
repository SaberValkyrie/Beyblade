package com.example.demo.dto;

import com.example.demo.entity.BeyBlade;
import com.example.demo.entity.TypeBey;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Register {
    public String username;
    public String avatar;
    public boolean isDie;
    public TypeBey type;
    public Timestamp createTime;
    public boolean is;
    public BeyBlade bey;
    public int hp;
    public byte buff;
}
