package com.example.demo.entity;

import javax.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "prize_cua_toi")
public class MyPrize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_voucher_id")
    public int myVoucherId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    public Prize prize;

    @Column(name = "status")
    public boolean status;

    @Column(name = "time_save")
    public Timestamp timeSave;
}
