package com.example.demo.entity;

import javax.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;


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
}