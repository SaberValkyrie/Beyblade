package com.example.demo.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "prize")
public class Prize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "img")
    public String img;

    @Column(name = "type")
    public byte type;
}
