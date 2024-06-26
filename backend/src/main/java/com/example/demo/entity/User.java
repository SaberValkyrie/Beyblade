package com.example.demo.entity;

import javax.persistence.*;

import lombok.Data;

import java.sql.Timestamp;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public int userId;

    @Column(name = "account_id")
    public int accountId;

    @Column(name = "username")
    public String username;

    @Column(name = "avatar")
    public String avatar;

    @Column(name = "rank")
    public byte rank;

    @Column(name = "created_at")
    public Timestamp createdAt;

    @Column(name = "ma_xac_nhan")
    public String code;

    @Column(name = "diem")
    public int diem;

    @Column(name = "active")
    public boolean active;


    @Column(name = "diemdanh")
    public boolean diemdanh;

    @Column(name = "diemdanhVIP")
    public boolean diemdanhVIP;
}