package com.example.demo.repository.product;

import com.example.demo.entity.MyPrize;
import com.example.demo.entity.Prize;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MyVoucherRepository extends JpaRepository<MyPrize,Long> {


   @Query("select m from MyPrize m where m.prize.type = :i and m.user.username = :username and  m.soluong > 0")
   List<MyPrize> getMyPrizeByStatus(String username, byte i);
}
