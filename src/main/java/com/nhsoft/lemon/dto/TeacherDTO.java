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
@ApiModel("教师表实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO implements Serializable {

    private static final long serialVersionUID = 2382952643461937908L;

    @ApiModelProperty("教师id")
    private Long teachId;

    /**
     * 教师姓名
     */
    @ApiModelProperty("教师姓名")
    private String teachName;

    /**
     * 教师编号
     */
    @ApiModelProperty("教师编号")
    private String teachNo;

    /**
     * 教师手机号
     */
    @ApiModelProperty("教师手机号")
    private String teachPhone;

}
