package com.example.demo.support;

import com.example.demo.entity.Items;
import com.example.demo.service.BeyService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalTime;


@Component
public class Auto {
    private final long timeUpdate = 360000;//60p

    @Autowired
    private UserService userService;

    @Autowired
    private BeyService beyService;
        @Scheduled(cron = "0 * * * * *") // Chạy vào mỗi phút
//@Scheduled(cron = "*/30 * * * * *")//MỖI 30S
// @Scheduled(cron = "* * * * * *")//MỖI S
    public void checkVipExpiration() {

            Timestamp cur = new Timestamp(System.currentTimeMillis());
            for (Items items : userService.getAllItems()){
                if (!items.vinhvien && items.ngayhethan.before(cur)){
                    userService.deteleItem(items);
                }
            }


    }

    @Scheduled(cron = "0 0 * * * *") // Chạy mỗi giờ
    public void resetShop() {
        beyService.loadShop = true;
        beyService.loadBoss = true;
        beyService.loadTop = true;
        System.out.println("Reset Shop /TOP thành công");
    }

}