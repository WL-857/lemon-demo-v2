package com.nhsoft.lemon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 教师实体类
 * @author wanglei
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teacher")
public class Teacher implements Serializable {
    private static final long serialVersionUID = 6663176201485254330L;
    /**
     * 教师id，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long teachId;

    /**
     * 教师姓名
     */

    private String teachName;

    /**
     * 教师编号
     */

    private String teachNo;

    /**
     * 教师手机号
     */

    private String teachPhone;

    @ManyToMany(cascade = CascadeType.ALL)
    //joinColumn：当前类的主键，inverseJoinColumns：关联类的主键
    @JoinTable(name = "teacher_course_mapping",joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Course> courses = new ArrayList<>();
}
