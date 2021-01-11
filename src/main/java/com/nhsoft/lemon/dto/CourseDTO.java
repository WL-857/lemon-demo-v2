package com.nhsoft.lemon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 课程实体类
 * @author wanglei
 */
@ApiModel("课程实体")
@Data
public class CourseDTO implements Serializable {

    private static final long serialVersionUID = 7305069259917484908L;
    /**
     * 课程id
     */
    @ApiModelProperty("课程id")
    private Long couId;

    /**
     * 课程名称
     */
    @ApiModelProperty("课程名称")
    private String couName;

    /**
     * 课程编号
     */
    @ApiModelProperty("课程编号")
    private String couNo;
}
