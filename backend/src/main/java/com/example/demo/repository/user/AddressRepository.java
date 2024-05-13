package com.example.demo.repository.user;

import com.example.demo.entity.AddressInfo;
import com.example.demo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository extends JpaRepository<AddressInfo, Long> {

    @Query("select a from AddressInfo a where a.addressId =:addressId ")
    AddressInfo getAddressInfoByAddressId(long addressId);

}
