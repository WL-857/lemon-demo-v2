package com.nhsoft.lemon.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author wanglei
 */
@Data
@Entity
@Table(name = "teacher_course_mapping")
public class TeacherCourse implements Serializable {

    private static final long serialVersionUID = 6780613568368598072L;
    /**
     * 教师id
     */
    @Id
    @Column(name = "t_id")
    private Long teachId;
    /**
     * 课程id
     */
    @Id
    @Column(name = "c_id")
    private Long couId;
}
