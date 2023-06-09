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
 * @since 2023-04-25
 */
@TableName("select_class")
@ApiModel(value = "SelectClass对象", description = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SelectClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    private Integer studentId;

    private Integer openRecord;

    private Integer usuallyScore;

    private Integer testScore;

    private Integer totalScore;

    @TableField(exist = false)
    private String className;

    @TableField(exist = false)
    private String classId;

    @TableField(exist = false)
    private String teacherName;

    @TableField(exist = false)
    private String teacherId;

    @TableField(exist = false)
    private String score;

    @TableField(exist = false)
    private String time;

    @TableField(exist = false)
    private String studentName;

    @TableField(exist = false)
    private String Term;
}
