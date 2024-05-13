package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    public int accountId;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;

    @Column(name = "is_ban")
    public boolean ban;

    @Column(name = "coint")
    public double coint;

    @Column(name = "role")
    public byte role;

    @Column(name = "created_at")
    public Timestamp createdAt;

    public Account() {

    }
}
