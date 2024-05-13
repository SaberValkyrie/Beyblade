package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "message")
public class ChatGlobal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long messageId;

    @ManyToOne
    @JoinColumn(name = "user_chat")
    public User user;

    @Column(name = "text")
    public String text;

    @Column(name = "time_chat")
    public Timestamp timeChat;

    @Column(name = "violate")
    public boolean violate;

    @Column(name = "status")
    public boolean status;

}
