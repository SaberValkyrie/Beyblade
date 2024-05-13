package com.example.demo.repository.product;

import com.example.demo.entity.MyVoucher;
import com.example.demo.entity.User;
import com.example.demo.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MyVoucherRepository extends JpaRepository<MyVoucher,Long> {

    @Query("select m from MyVoucher m where m.voucher = :voucher and m.user =:user")
    MyVoucher getMyVoucherByCode(Voucher voucher,User user);

    @Query("select mv.voucher from MyVoucher mv where mv.voucher.countLeft > 0 order by mv.timeSave desc")
    List<Voucher> findByUser(User userToken);

    @Query("select mv.voucher from MyVoucher " +
            "mv where mv.voucher.countLeft > 0" +
            " and mv.voucher.id = :id" +
            " order by mv.timeSave desc")
    Voucher findById(long id);

    @Query("select mv.voucher from MyVoucher mv where mv.status = false order by mv.timeSave desc")
    List<Voucher> getVoucherUsed(User userToken);
}
