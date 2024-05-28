package com.example.demo.support;

import com.example.demo.dto.ResponseOpject;
import com.example.demo.entity.BeyBlade;
import com.example.demo.entity.Order;
import com.example.demo.service.BeyService;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Util {
    private static final Random rand;

    static {
        rand = new Random();
    }










    public static int nextInt(int from, int to) {
        return from + rand.nextInt(to - from + 1);
    }
    public static int nextInt(int max) {
        return rand.nextInt(max);
    }

    public static JsonElement convertStringToJson(String jsonString) {
            JsonParser parser = new JsonParser();
            try {
                return parser.parse(jsonString);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }





public static String getNowString(){
        return LocalTime.now().getHour() + " Giờ " + LocalTime.now().getMinute() +" Phút";
}



    public static int getRandomArray( int[] names){
        Random random = new Random();
        int index = random.nextInt(names.length);
        int set = names[index];
        return set;
    }
    public static boolean isTrue(int ratio, int typeRatio) {
        int num = Util.nextInt(typeRatio);
        if (num < ratio) {
            return true;
        }
        return false;
    }
    public static boolean canDoWithTime(long lastTime, long miniTimeTarget) {
        return System.currentTimeMillis() - lastTime > miniTimeTarget;
    }

    public static String generateRandomText(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                + "lmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String key(String token) {
        String random = String.valueOf(UUID.randomUUID());
        String keyw = token.substring(0,5) + Util.nextInt(1,100000000) + random.substring(0,6);
        return keyw;
    }

    public static boolean isTrue(float ratio, int typeRatio) {
        if (ratio < 1) {
            ratio *= 10;
            typeRatio *= 10;
        }
        int num = Util.nextInt(typeRatio);
        if (num < ratio) {
            return true;
        }
        return false;
    }
    public static String numberToMoney(long power) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat num = NumberFormat.getInstance(locale);
        num.setMaximumFractionDigits(1);
        if (power >= 1000000000) {
            return num.format((double) power / 1000000000) + " Tỷ";
        } else if (power >= 1000000) {
            return num.format((double) power / 1000000) + "Tr";
        } else if (power >= 1000) {
            return num.format((double) power / 1000) + "K";
        } else {
            return num.format(power);
        }
    }

    public static int timeLeft(int militime,long st){
        return (int) ((militime - (System.currentTimeMillis() - st) ) / 1000);
    }

    public static String convert(long power) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat num = NumberFormat.getInstance(locale);
        num.setMaximumFractionDigits(1);
        if (power >= 1000000000) {
            return num.format((double) power / 1000000000) + " Tỷ";
        } else if (power >= 1000000) {
            return num.format((double) power / 1000000) + " Tr";
        } else if (power >= 1000) {
            return num.format((double) power / 1000) + " k";
        } else {
            return num.format(power);
        }
    }

    public static int getDistance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static ResponseEntity<ResponseOpject> checkStatus(HttpStatus status, String message, Object dataObject){
        String statustext = status == HttpStatus.OK ? "True" : "False";
        return ResponseEntity.status(status).body(new ResponseOpject(statustext,message,dataObject));
    }

    public static ResponseEntity<ResponseOpject> checkStatusRes(HttpStatus status, String message, Object dataObject) {
        ResponseOpject responseOpject = new ResponseOpject();
        responseOpject.status = (status.toString());
        responseOpject.message = (message);
        responseOpject.data = (dataObject);
        return new ResponseEntity<>(responseOpject, status);
    }


    public static ResponseEntity<List<ResponseOpject>> checkStatusList(HttpStatus httpStatus, String success, List<Object> dataObject) {
        String statustext = "True";
        if (!httpStatus.equals(HttpStatus.OK)){
            statustext = "False";
        }
        return ResponseEntity.status(httpStatus).body(Collections.singletonList(new ResponseOpject(statustext, success, (dataObject))));
    }


    public static Timestamp getTimestampYesterdayStart() {
        // Lấy ngày hôm qua
        LocalDate yesterday = LocalDate.now().minusDays(1);

        // Tạo một LocalDateTime từ ngày hôm qua và thời gian bắt đầu của ngày (00:00:00)
        LocalDateTime yesterdayStart = LocalDateTime.of(yesterday, LocalTime.MIN);

        // Chuyển đổi LocalDateTime thành Timestamp và trả về
        return Timestamp.valueOf(yesterdayStart);
    }


    public static Timestamp getTimestampTodayStart() {
        // Lấy ngày hôm nay
        LocalDate today = LocalDate.now();

        // Tạo một LocalDateTime từ ngày hôm nay và thời gian bắt đầu của ngày (00:00:00)
        LocalDateTime todayStart = LocalDateTime.of(today, LocalTime.MIN);

        // Chuyển đổi LocalDateTime thành Timestamp và trả về
        return Timestamp.valueOf(todayStart);
    }

//    public static boolean checkTime(){
//        LocalDate time = LocalDate.now();
//        if (time.atStartOfDay())
//    }


    public static boolean nay(Order order){
        // Chuyển timestamp của đơn hàng thành LocalDate
        LocalDate orderDate = order.date.toLocalDateTime().toLocalDate();
        // Lấy timestamp cho ngày hôm nay
        LocalDate homNayDate = LocalDate.now();

        return orderDate.equals(homNayDate);
    }

    public static boolean qua(Order order){
        // Chuyển timestamp của đơn hàng thành LocalDate
        LocalDate orderDate = order.date.toLocalDateTime().toLocalDate();
        // Lấy timestamp cho ngày hôm qua
        LocalDate homQuaDate = LocalDate.now().minusDays(1);

        return orderDate.equals(homQuaDate);
    }

}
