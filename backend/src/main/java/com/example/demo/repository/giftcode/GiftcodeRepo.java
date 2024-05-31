package com.example.demo.repository.giftcode;

import com.example.demo.entity.GIFTCODE;
import com.example.demo.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface GiftcodeRepo extends JpaRepository<GIFTCODE,Long> {

    @Query("select c from GIFTCODE c where c.code=:code")
    GIFTCODE findCode(String code);
}
