package com.example.demo.repository.giftcode;

import com.example.demo.entity.GIFTCODE;
import com.example.demo.entity.GiftcodeHistory;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface HistoryRepo extends JpaRepository<GiftcodeHistory,Long> {

    @Query("select h from GiftcodeHistory h where h.giftcode.code=:code")
    GiftcodeHistory getHistoryByCodeAndUser(String code);
}
