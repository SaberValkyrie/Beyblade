package com.example.demo.dto;

import com.example.demo.entity.BeyBlade;
import lombok.Data;

@Data
public class CheckOption {
    public BeyBlade me;
    public BeyBoss boss;

    public BeyDame dameMe;
    public BeyDame dameBoss;

    public byte pointMe;
    public byte pointBoss;

    public BeyBlade win;

}
