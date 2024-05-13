package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "notify")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    public String title;

    @Column(name = "content")
    public String content;

//    @Column(name = "user_id")

//    @ManyToOne
    @JoinColumn(name = "user_id")
    public int userId;


    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "read_status")
    private boolean readStatus;

    @Column(name = "note")
    private String note;
}

