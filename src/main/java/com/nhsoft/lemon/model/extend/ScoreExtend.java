package com.nhsoft.lemon.model.extend;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wanglei
 */
@Data
public class ScoreExtend implements Serializable {
    private static final long serialVersionUID = -3915938700321256171L;

    /**
     * 成绩分数
     */
    private BigDecimal grade;

    /**
     * 科目名称
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

    public ScoreExtend(BigDecimal grade, String couName, String time, String stuName) {
        this.grade = grade;
        this.couName = couName;
        this.time = time;
        this.stuName = stuName;
    }

//    stu.stuName,c.couName,s.grade,s.time


}
