package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "items")
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @ManyToOne
    @JoinColumn(name = "user")
    public User user;

    @ManyToOne
    @JoinColumn(name = "beyblade")
    public BeyBlade beyBlade;

    @Column(name = "create_time")
    public Timestamp create_time;

    @Column(name = "hsd")
    public Timestamp ngayhethan;

    @Column(name = "vv")
    public boolean vinhvien;


    @Column(name = "selectedBey")
    public boolean selectedBey;
}
