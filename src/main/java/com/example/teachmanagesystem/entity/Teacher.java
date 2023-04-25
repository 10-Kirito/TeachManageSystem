package com.example.teachmanagesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author Kirito
 * @since 2023-04-12
 */
@ApiModel(value = "Teacher对象", description = "")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "teacher_id", type = IdType.AUTO)
    private Integer teacherId;
    private String teacherName;
    private String gender;
    private LocalDate birthday;
    private String position;
    private Integer departId;

    // 1. 定义多表联接查询所需要的相关字段，这里的注解是告诉数据库该字段在数据库中并不存在
    @TableField(exist = false)
    private String departName;
}

