package com.example.teachmanagesystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-12
 */
public interface IStudentService extends IService<Student> {

    Page<Student> getAllStudents(Page<Object> page, Integer studentId, String studentName, String gender, String departName, String status);
    
}
