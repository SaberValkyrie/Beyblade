package com.example.demo.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity.
 *
 * @author Nguyễn Hải
 * Created 13/03/2024
 */

@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    protected Date createTime;
    protected Date updateTime;
}