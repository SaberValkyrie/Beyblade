package com.example.demo.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
    @Data
    @Table(name = "address_info")
    public class AddressInfo {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "address_id")
        public long addressId;

        @ManyToOne
        @JoinColumn(name = "user_id")
        public User user;

        @Column(name = "fullname")
        public String fullname;

        @Column(name = "phone")
        public String phone;

        @Column(name = "address")
        public String addressDetails;

        @Column(name = "thanhpho")
        public String thanhPho;//thành phố

        @Column(name = "quanhuyen")
        public String quanHuyen;// quận huyện

        @Column(name = "phuongxa")
        public String phuongXa; // phường xá

        @Column(name = "is_default")
        public boolean isDefault;


}
