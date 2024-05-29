package com.example.demo.repository.product;

import com.example.demo.entity.MyPrize;
import com.example.demo.entity.Prize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface VoucherRepository extends JpaRepository<Prize,Long> {

    @Query("select p from Prize p where p.id =:i")
    Prize getPrizeByID(int i);

    @Query("select p from MyPrize p where p.prize.id =:id and p.soluong > 0")
    MyPrize getMYPrizeByID(int id);
}
