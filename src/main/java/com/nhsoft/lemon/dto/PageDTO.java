package com.nhsoft.lemon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("分页查询相关数据")
@Data
public class PageDTO implements Serializable {
    private static final long serialVersionUID = 4582231596434692389L;

    @ApiModelProperty("分页数据起始位置")
    public int pageNo = 0;

    @ApiModelProperty("每页数据量")
    public int pageSize = 5;
}
