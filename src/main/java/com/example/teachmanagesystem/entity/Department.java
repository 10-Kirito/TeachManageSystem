package com.example.teachmanagesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-04-12
 */
@ApiModel(value = "Department对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "depart_id", type = IdType.AUTO)
    private Integer departId;
    private String departName;
    private String departPlace;
    private String departPhone;
}
