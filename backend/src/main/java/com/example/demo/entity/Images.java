package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "images_id")
    public Long imageId;

    @ManyToOne
    @JoinColumn(name = "product")
    public Product product;

    @Column(name = "name")
    public String name;

    @Column(name = "is_default")
    public byte isDefault;
}
