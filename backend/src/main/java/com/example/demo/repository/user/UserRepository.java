package com.example.demo.repository.user;

import com.example.demo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select a from Account a where a.username = :username")
    Account findUsername (String username);
    @Query("select a from AddressInfo a where a.user = :user")
    List<AddressInfo> getAddress(User user);

    User findByUsername(String username);

    @Query("select a from User a where a.accountId = :accountId")
    User findByAccountId(int accountId);

    @Query("select a from Account a where a.username = :username")
    Account getAccount(String username);



    @Query("select v.voucher from MyVoucher v where v.user = :user order by v.voucher.percent desc ")
    List<Voucher> getVoucher(User user);

    @Query("select v from Voucher v where v.voucher_code = :code")
    Voucher getVoucherByCode(String code);



}
