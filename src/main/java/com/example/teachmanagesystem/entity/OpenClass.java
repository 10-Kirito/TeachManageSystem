package com.example.teachmanagesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @since 2023-04-19
 */
@TableName("open_class")
@ApiModel(value = "OpenClass对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OpenClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer teacherId;
    private Integer capacity;
    private Integer enrollment;
    private String time;
    private String location;
    private String term;

    @TableField(exist = false)
    private String classId;

    @TableField(exist = false)
    private String className;

    @TableField(exist = false)
    private int score;

    @TableField(exist = false)
    private String teacherName;
    @TableField(exist = false)
    private String position;
}
