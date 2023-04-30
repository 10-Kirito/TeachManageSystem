package com.example.teachmanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.entity.Student;
import com.example.teachmanagesystem.entity.login.StudentPasswords;
import com.example.teachmanagesystem.mapper.StudentMapper;
import com.example.teachmanagesystem.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-12
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public Page<Student> getAllStudents(Page<Object> page, Integer studentId, String studentName, String gender, String departName, String status) {
        return studentMapper.getAllStudents(page, studentId, studentName, gender, departName, status);
    }



    // 1. 获取所有学生的详细信息
}
