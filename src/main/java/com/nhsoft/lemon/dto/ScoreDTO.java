package com.nhsoft.lemon.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wanglei
 */
@ApiModel("成绩实体")
@Data
@AllArgsConstructor
public class ScoreDTO implements Serializable {
    private static final long serialVersionUID = -8669081549572749368L;


    /**
     * 科目的成绩
     */
    private BigDecimal grade;

    /**
     * 课程名称
     */
    private String couName;

    /**
     * 学年
     */
    private String time;

    /**
     * 学生姓名
     */
    private String stuName;


}
