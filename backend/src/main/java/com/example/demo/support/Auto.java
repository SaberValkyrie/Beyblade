package com.example.demo.support;

import com.example.demo.entity.Items;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Component
public class Auto {
    private final long timeUpdate = 360000;//60p

    @Autowired
    private UserService userService;
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

            System.out.println("Bây h là "+ new Timestamp(System.currentTimeMillis()));

    }
}