package com.nhsoft.lemon.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 成绩实体类
 * @author wanglei
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "score")
public class Score implements Serializable {
    private static final long serialVersionUID = 5568052051145119793L;

    /**
     * 成绩id，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long scoreId;

    /**
     * 学生id
     */
    @Column(name = "s_id")
    private Long studentId;

    /**
     * 课程id
     */
    @Column(name = "c_id")
    private Long courseId;

    /**
     * 科目的成绩
     */
    private BigDecimal grade;

    /**
     * 学期时间
     */
    private String time;


}
