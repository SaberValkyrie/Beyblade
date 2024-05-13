package com.example.demo.repository.user;

import com.example.demo.entity.ChatGlobal;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MesssageRepository extends JpaRepository<ChatGlobal, Long> {


}
