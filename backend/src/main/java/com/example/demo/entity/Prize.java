package com.example.demo.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "prize")
public class Prize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    public byte id;

    @Column(name = "name")
    public String name;

}
