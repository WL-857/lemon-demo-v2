package com.nhsoft.lemon.model.extend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wanglei
 */
@Data
@AllArgsConstructor
public class TeacherExtend implements Serializable {
    /**
     * 学科最高分数
     */
    private BigDecimal maxScore;

    /**
     * 学科最小分数
     */
    private BigDecimal minScore;

    /**
     * 学科平均分数
     */
    private Double avgScore;

    /**
     * 科目名称
     */
    private String couName;

    /**
     * 教师姓名
     */
    private String teachName;

    /**
     * 学年
     */
    private String time;

    public TeacherExtend(String couName, BigDecimal maxScore, BigDecimal minScore, Double avgScore) {
        this.couName = couName;
        this.maxScore = maxScore;
        this.minScore = minScore;
        this.avgScore = avgScore;
    }
    public TeacherExtend(String couName, BigDecimal maxScore, BigDecimal minScore, String time,Double avgScore) {
        this.couName = couName;
        this.maxScore = maxScore;
        this.minScore = minScore;
        this.avgScore = avgScore;
        this.time = time;
    }

    public TeacherExtend(String teachName,String couName ) {
        this.couName = couName;
        this.teachName = teachName;
    }
}
