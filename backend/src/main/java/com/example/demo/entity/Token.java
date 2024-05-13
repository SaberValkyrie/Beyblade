package com.example.demo.entity;

import com.example.demo.constant.DateConstant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

import lombok.*;

import java.util.Date;
import java.util.UUID;

/**
 * Token.
 *
 * @author Nguyễn Hải
 * Created 13/03/2024
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`token`")
public class Token extends BaseEntity{
    @Column(name = "`CODE`", nullable = false, length = 50)
    public String code;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    public User user;

    @Column(name = "expiry")
    public Date expiry;

    @PrePersist
    public void prePersist() {
        this.createTime = DateConstant.currentDate();
        this.expiry = DateConstant.getDay(DateConstant.DEFAULT_EXPIRY);
        this.code = UUID.randomUUID().toString();
    }
}