package com.example.demo.entity;

import lombok.Data;

@Data
public class Greeting
{

    public String content;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

}
