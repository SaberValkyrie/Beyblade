package com.example.demo.constant;

public class AccountConstant {
    public static final String TABLE_NAME = "account"; // tên bảng
    public static final Integer GOLD_DEFAULT = 0; // số thỏi vàng mặc định khi đăng ký tài khoản
    public static final Integer STATUS_ACTIVE_MEMBER = 1; // trạng thái tài khoản thành viên là Ngọc rồng X
    public static final Integer STATUS_INACTIVE_MEMBER = 0; // trạng thái tài khoản thành viên chưa là Ngọc rồng X
    public static final Integer ROLE_DEFAULT = -1;
    public static final Integer DEFAULT_POINT_POST = 0;
    public static final Short STATUS_BAN = 0;
    public static final Integer NEW_REG_DEFAULT = 0;
    public static final Integer SERVER_LOGIN_DEFAULT = -1;

    public static final Integer MAX_ACCOUNT_OF_IP = 5; // số lượng tài khoản tối đa của 1 ip trong 1 ngày

    public static final Long TIME_EXPIRED_CODE = 300_000L; // thời gian hết hạn của code xác nhận mail (10 phút)
}
