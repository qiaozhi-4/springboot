package com.zking.entity;

import lombok.*;

import javax.persistence.*;

//自动生成get/set/toString
@Entity
@Getter @Setter @ToString
//自动生成全参构造函数
@AllArgsConstructor
//自动生成无参构造函数
@NoArgsConstructor
//表示为数据库的表，name为表名
@Table(name = "user2")
public class User {
    //数据库的主键
    @Id
    //数据库的列
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private Double money;
}

