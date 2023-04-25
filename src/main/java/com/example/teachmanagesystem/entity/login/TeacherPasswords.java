package com.example.teachmanagesystem.entity.login;

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
@TableName("teacher_passwords")
@ApiModel(value = "TeacherPasswords对象", description = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeacherPasswords implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer teacherId;

    private String passwordHash;
}
