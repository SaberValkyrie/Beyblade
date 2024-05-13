package com.example.demo.service;

import com.example.demo.entity.Notification;
import com.example.demo.entity.User;
import com.example.demo.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyService {

    @Autowired
    private NotificationRepository notificationRepository;
    public List<Notification> getAllNotify(User user,boolean read_status) {
        return notificationRepository.getAllNotifyByStatus(user.userId,read_status);
    }
    public List<Notification> getAllNotification(User user) {
        return notificationRepository.getAll(user.userId);
    }
}
