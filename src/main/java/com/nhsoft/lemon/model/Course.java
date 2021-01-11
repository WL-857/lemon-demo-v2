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
 * 课程实体类
 * @author wanglei
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = 7305069259917484908L;
    /**
     * 课程id,也是主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long courseId;

    /**
     * 课程名称
     */

    private String courseName;

    /**
     * 课程编号
     */

    private String courseNo;

    public Course(String couName, String couNo) {
        this.courseName = couName;
        this.courseNo = couNo;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    //joinColumn：当前类的主键，inverseJoinColumns：关联类的主键
    @JoinTable(name = "score",joinColumns = @JoinColumn(name = "c_id"),
            inverseJoinColumns = @JoinColumn(name = "s_id"))
    private List<Course> course = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    //joinColumn：当前类的主键，inverseJoinColumns：关联类的主键
    @JoinTable(name = "teacher_course_mapping",joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Teacher> teachers = new ArrayList<>();
}
