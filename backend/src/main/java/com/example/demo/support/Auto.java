package com.example.demo.support;

import com.example.demo.entity.BeyBlade;
import com.example.demo.entity.Items;
import com.example.demo.entity.TOP;
import com.example.demo.entity.User;
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
            for (TOP top : beyService.getTop()){
                if (top.endBuff.before(new Timestamp(System.currentTimeMillis()))){
                    top.buff = 0;
                    beyService.saveTop(top);
                }
            }
            for (Items items : userService.getAllItems()){
                if (!items.vinhvien && items.ngayhethan.before(cur)){//item hsd và tới ngày hết hạn
                        System.out.println("delete item hsd");
                        userService.deteleItem(items);//bey chưa sử dụng thì xóa

                }
            }
    }

    @Scheduled(cron = "0 0 * * * *") // Chạy mỗi giờ
    public void resetShop() {
           if (LocalTime.now().getHour() == 1){
               for(User user : userService.getAllUser()){
                   user.diemdanh = false;
                   user.diemdanhVIP = false;
                   user.diem = 0;
                   userService.saveUser(user);
               }
               System.out.println("Reset Điểm Danh: " + Util.getNowString());
           }
           for (TOP top : beyService.getTop()){
               if (top.endBuff.before(new Timestamp(System.currentTimeMillis()))){
                   top.buff = 0;
                   beyService.saveTop(top);
               }
           }
        for (User user : userService.getAllUser()){
            if(user.diem > 0){
                user.diem = 0;
            }
        }
        beyService.loadShop = true;
        beyService.loadBoss = true;
        beyService.loadTop = true;
        System.out.println("Reset thành công: " +  Util.getNowString());
    }



}