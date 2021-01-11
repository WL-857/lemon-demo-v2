package com.nhsoft.lemon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wanglei
 */
@ApiModel("学生表实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO implements Serializable {

    private static final long serialVersionUID = 2382952643461937908L;



    @ApiModelProperty("学生id")
    private Long stuId;

    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String stuName;

    /**
     * 学生性别
     */
    @ApiModelProperty("学生性别")
    private Integer stuSex;

    /**
     * 学生学号
     */
    @ApiModelProperty("学生学号")
    private String stuNo;

    /**
     * 学生手机号
     */
    @ApiModelProperty("学生手机号")
    private String stuPhone;
}
