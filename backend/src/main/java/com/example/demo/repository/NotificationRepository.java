package com.example.demo.repository;

import com.example.demo.entity.Notification;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    @Query("select n from Notification n" +
            " where n.userId = :userId " +
            "order by n.createdAt desc ")
    List<Notification> getAll(int userId);


    @Query("select n from Notification n" +
            " where n.userId = :userId " +
            "and  n.readStatus = :readStatus " +
            "order by n.createdAt desc ")
    List<Notification> getAllNotifyByStatus(int userId, boolean readStatus);
}
