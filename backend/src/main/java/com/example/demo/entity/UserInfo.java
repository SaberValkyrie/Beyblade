package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "users_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

//    @OneToOne
//    @JoinColumn(name = "user_id")
//    public User user;

    @Column(name = "user_id")
    public int userId;

    @Column(name = "shopee_name", nullable = false)
    public String shopeeName;

    @Column(name = "fullname", nullable = false)
    public String fullname;

    @Column(name = "email", nullable = false)
    public String email;

    @Column(name = "phone")
    public String phone;

    @Column(name = "gender")
    public String gender;

    @Column(name = "date")
    public Timestamp date;

    @Column(name = "updated_time", columnDefinition = "TIMESTAMP DEFAULT current_timestamp() ON UPDATE current_timestamp()")
    public Timestamp updatedTime;


}
