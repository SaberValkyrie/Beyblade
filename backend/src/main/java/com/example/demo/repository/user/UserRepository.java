package com.example.demo.repository.user;

import com.example.demo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
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


    @Query("select i from Items i where i.user =:userToken order by i.create_time desc")
    ArrayList<Items> getItemByUser(User userToken);
    @Query("select i.beyBlade from Items i where i.user =:userToken and i.beyBlade.type.id =:type order by i.create_time desc")
    ArrayList<BeyBlade> getItemsByUserAndType(User userToken,byte type);

    @Query("select i from Items i")
    ArrayList<Items> getAllItems();

}
