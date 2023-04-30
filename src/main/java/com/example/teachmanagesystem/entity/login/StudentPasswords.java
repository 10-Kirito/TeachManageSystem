package com.example.teachmanagesystem.entity.login;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-04-18
 */
@TableName("student_passwords")
@ApiModel(value = "StudentPasswords对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentPasswords implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "student_id", type = IdType.AUTO)
    private Integer studentId;

    private String passwordHash;
}
