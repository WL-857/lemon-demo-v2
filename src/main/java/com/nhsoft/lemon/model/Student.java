package com.nhsoft.lemon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 学生实体类
 * @author wanglei
 */
@Entity
@Table(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    private static final long serialVersionUID = -8077158237459001744L;
    /**
     * 学生id,也是主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long stuId;

    /**
     * 学生姓名
     */

    private String stuName;

    /**
     * 学生性别
     */

    private Integer stuSex;

    /**
     * 学生学号
     */

    private String stuNo;

    /**
     * 学生手机号
     */

    private String stuPhone;

    @ManyToMany(cascade = CascadeType.ALL)
    //joinColumn：当前类的主键，inverseJoinColumns：关联类的主键
    @JoinTable(name = "score",joinColumns = @JoinColumn(name = "s_id"),
    inverseJoinColumns = @JoinColumn(name = "c_id"))
    private List<Course> courses = new ArrayList<>();
}
