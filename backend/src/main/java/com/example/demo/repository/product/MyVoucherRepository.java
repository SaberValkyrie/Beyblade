package com.example.demo.repository.product;

import com.example.demo.entity.MyPrize;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MyVoucherRepository extends JpaRepository<MyPrize,Long> {

}
