package com.example.teachmanagesystem.service.login.impl;

import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.common.APIStatusCode;
import com.example.teachmanagesystem.entity.login.StudentPasswords;
import com.example.teachmanagesystem.mapper.login.StudentPasswordsMapper;
import com.example.teachmanagesystem.service.login.IStudentPasswordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-18
 */
@Service
public class StudentPasswordsServiceImpl extends ServiceImpl<StudentPasswordsMapper, StudentPasswords> implements IStudentPasswordsService {

    @Override
    public APIResponse<?> resetPassword(Integer studentId) {
        StudentPasswords studentPasswords = getById(studentId);
        studentPasswords.setPasswordHash("123456");

        return new APIResponse<>(updateById(studentPasswords), APIStatusCode.SUCCESS, "重置密码成功!");
    }
}
