package com.example.demo.dto;

import com.example.demo.entity.BeyBlade;
import lombok.Data;

import javax.persistence.*;


@Data
public class BeyBoss {

    public long id;

    public BeyBlade bey;

    public byte buff;

    public byte time;

    public long hp;
    public int dame;
}