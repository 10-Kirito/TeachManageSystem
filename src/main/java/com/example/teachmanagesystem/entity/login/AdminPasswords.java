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
@TableName("admin_passwords")
@ApiModel(value = "AdminPasswords对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminPasswords implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer adminId;

    private String passwordHash;
}
