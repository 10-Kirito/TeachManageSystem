package com.example.teachmanagesystem.controller.login;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.common.APIStatusCode;
import com.example.teachmanagesystem.entity.Student;
import com.example.teachmanagesystem.entity.Teacher;
import com.example.teachmanagesystem.entity.login.StudentPasswords;
import com.example.teachmanagesystem.entity.login.TeacherPasswords;
import com.example.teachmanagesystem.service.IStudentService;
import com.example.teachmanagesystem.service.ITeacherService;
import com.example.teachmanagesystem.service.login.IStudentPasswordsService;
import com.example.teachmanagesystem.service.login.ITeacherPasswordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kirito
 * @since 2023-04-18
 */
@RestController
@RequestMapping("/teacher-passwords")
public class TeacherPasswordsController {
    @Autowired
    ITeacherPasswordsService iTeacherPasswordsService;

    @Autowired
    ITeacherService teacherService;

    @GetMapping("/login")
    public APIResponse<Teacher> login(@RequestParam long id,
                                      @RequestParam String password){

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", id);
        Teacher teacher = teacherService.getOne(queryWrapper);
        if(teacher == null)
            return new APIResponse<Teacher>(null, APIStatusCode.NOT_FOUND, "该用户不存在!");
        else {
            QueryWrapper<TeacherPasswords> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("teacher_id", teacher.getTeacherId());
            queryWrapper1.eq("password_hash", password);
            TeacherPasswords teacherPasswords = iTeacherPasswordsService.getOne(queryWrapper1);
            if (teacherPasswords == null)
                return new APIResponse<Teacher>(null, APIStatusCode.FORBIDDEN, "用户密码错误!");
            else
                return new APIResponse<Teacher>(teacher, APIStatusCode.SUCCESS, "登陆成功!");
        }
    }
}
