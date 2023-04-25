package com.example.teachmanagesystem.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "Class对象", description = "")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String classId;
    private String className;
    private Integer classScore;
    private Integer classTime;
    private Integer departId;

    @TableField(exist = false)
    private String departName;
}
