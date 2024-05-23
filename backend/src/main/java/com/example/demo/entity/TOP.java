package com.example.demo.entity;

import com.example.demo.entity.BeyBlade;
import com.example.demo.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "top")
public class TOP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank")
    public short rank;

    @ManyToOne
    @JoinColumn(name = "user")
    public User user;

    @ManyToOne
    @JoinColumn(name = "selectBey")
    public BeyBlade selectBey;

    @Column(name = "buff")
    public byte buff;

    @Column(name = "top")
    public int top;

    @Column(name = "win")
    public short win;

    @Column(name = "lost")
    public short lost;

    @Column(name = "created_at")
    public Timestamp createdAt;

    @Column(name = "end_buff")
    public Timestamp endBuff;

}
