package com.example.teachmanagesystem.controller.login;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.common.APIStatusCode;
import com.example.teachmanagesystem.entity.Admins;
import com.example.teachmanagesystem.entity.Student;
import com.example.teachmanagesystem.entity.login.AdminPasswords;
import com.example.teachmanagesystem.entity.login.StudentPasswords;
import com.example.teachmanagesystem.service.IAdminsService;
import com.example.teachmanagesystem.service.IStudentService;
import com.example.teachmanagesystem.service.login.IAdminPasswordsService;
import com.example.teachmanagesystem.service.login.IStudentPasswordsService;
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
@RequestMapping("/student-passwords")
public class StudentPasswordsController {


    @Autowired
    IStudentPasswordsService iStudentPasswordsService;

    @Autowired
    IStudentService studentService;

    @GetMapping("/login")
    public APIResponse<Student> login(@RequestParam long id,
                                     @RequestParam String password){

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", id);
        Student student = studentService.getOne(queryWrapper);
        if(student == null)
            return new APIResponse<Student>(null, APIStatusCode.NOT_FOUND, "没有该用户!");
        else {
            QueryWrapper<StudentPasswords> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("student_id", student.getStudentId());
            queryWrapper1.eq("password_hash", password);
            StudentPasswords studentPasswords = iStudentPasswordsService.getOne(queryWrapper1);
            if (studentPasswords == null)
                return new APIResponse<Student>(null, APIStatusCode.FORBIDDEN, "用户密码错误!");
            else
                return new APIResponse<Student>(student, APIStatusCode.SUCCESS, "登陆成功!");
        }
    }

    @GetMapping("/reset")
    public APIResponse<?> resetPassword(@RequestParam Integer studentId){

        return iStudentPasswordsService.resetPassword(studentId);
    }
}
