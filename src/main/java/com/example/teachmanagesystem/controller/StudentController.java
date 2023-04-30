package com.example.teachmanagesystem.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.common.APIStatusCode;
import com.example.teachmanagesystem.entity.Student;
import com.example.teachmanagesystem.service.IStudentService;
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
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    IStudentService iStudentService;

    @GetMapping("/getallstudents")
    public APIResponse<?> getAllStudents(@RequestParam(defaultValue = "1") Integer currentPage,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         @RequestParam(required = false) Integer studentId,
                                         @RequestParam(required = false) String studentName,
                                         @RequestParam(required = false) String gender,
                                         @RequestParam(required = false) String departName,
                                         @RequestParam(required = false) String status){
        Page<Student> page = iStudentService.getAllStudents(new Page<>(currentPage, pageSize), studentId, studentName, gender, departName, status);
        return new APIResponse<>(page, APIStatusCode.SUCCESS, "查询成功");
    }

}
