package com.example.demo.dto;

import com.example.demo.entity.BeyBlade;
import com.example.demo.entity.User;
import lombok.Data;


@Data
public class BeyBoss {

    public long id;

    public BeyBlade bey;

    public byte buff;

    public byte time;

    public long hp;

    public int dame;

    public User playerKill;
    public boolean die;
}