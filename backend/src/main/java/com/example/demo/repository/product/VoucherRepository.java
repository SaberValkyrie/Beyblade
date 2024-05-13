package com.example.demo.repository.product;

import com.example.demo.entity.MyVoucher;
import com.example.demo.entity.User;
import com.example.demo.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface VoucherRepository extends JpaRepository<Voucher,Long> {

}
