package com.zking.entity;

import lombok.*;

import javax.persistence.*;

//自动生成get/set/toString
@Entity
@Getter
@Setter
@ToString
//自动生成全参构造函数
@AllArgsConstructor
//自动生成无参构造函数
@NoArgsConstructor
//表示为数据库的表，name为表名
@Table(name = "employee")
public class Employee {
    //    ) ID主键、名字（字符串）、年龄（整数）、部门（字符串）、薪水（小数）
    //数据库的主键
    @Id
    //数据库的列
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer age;
    @Column
    private String department;
    @Column
    private Double salary;
}
