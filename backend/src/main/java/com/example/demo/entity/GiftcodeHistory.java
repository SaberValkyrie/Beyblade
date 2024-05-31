package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "giftcode_history")
public class GiftcodeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @ManyToOne
    @JoinColumn(name = "giftcode")
    public GIFTCODE giftcode;

    @ManyToOne
    @JoinColumn(name = "user")
    public User user;

}
