package com.example.demo.repository.product;

import com.example.demo.entity.Prize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface VoucherRepository extends JpaRepository<Prize,Long> {

    @Query("select p from Prize p where p.id =:i")
    Prize getPrizeByID(byte i);

}
