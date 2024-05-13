package com.example.demo.entity;

import javax.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "vouchers_cua_toi")
public class MyVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_voucher_id")
    public int myVoucherId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    public Voucher voucher;

    @Column(name = "status")
    public boolean status;

    @Column(name = "time_save")
    public Timestamp timeSave;
}
