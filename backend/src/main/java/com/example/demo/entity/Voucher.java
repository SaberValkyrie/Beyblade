package com.example.demo.entity;

import javax.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    public long id;

    @Column(name = "percent")
    public double percent;

    @Column(name = "voucher_code")
    public String voucher_code;
    @Column(name = "gia_toi_thieu")
    public double giaToiThieu;

    @Column(name = "start_time")
    public Timestamp startTime;

    @Column(name = "end_time")
    public Timestamp endTime;

    @Column(name = "apply_shop")
    public int apply;

    @Column(name = "type")
    public byte type;

    @Column(name = "count_left")
    public int countLeft;

}
