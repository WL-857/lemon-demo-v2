package com.nhsoft.lemon.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wanglei
 */
@Data
public class AvgMaxMinDTO implements Serializable {
    private static final long serialVersionUID = 1070176974613003243L;

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
     * 课程名称
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
}
