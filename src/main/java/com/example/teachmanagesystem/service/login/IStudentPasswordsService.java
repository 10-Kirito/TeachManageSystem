package com.example.teachmanagesystem.service.login;

import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.entity.login.StudentPasswords;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-18
 */
public interface IStudentPasswordsService extends IService<StudentPasswords> {

    APIResponse<?> resetPassword(Integer studentId);
}
