package com.nhsoft.lemon.model.extend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 课程实体类
 * @author wanglei
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseExtend implements Serializable {

    private static final long serialVersionUID = 7305069259917484908L;
    /**
     * 课程id,也是主键，自增
     */
    private Long couId;

    /**
     * 课程名称
     */

    private String couName;

    /**
     * 课程编号
     */

    private String couNo;
}
