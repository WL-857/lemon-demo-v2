package com.nhsoft.lemon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程实体类
 * @author wanglei
 */
@ApiModel("教师课程实体")
@Data
public class TeacherCourseDTO implements Serializable {

    private static final long serialVersionUID = 7305069259917484908L;
    /**
     * 教师姓名
     */
    @ApiModelProperty("教师姓名")
    private String teachName;

    /**
     * 课程名称
     */
    @ApiModelProperty("课程名称")
    private String couName;


}
