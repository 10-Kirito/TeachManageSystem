package com.example.teachmanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.entity.Teacher;
import com.example.teachmanagesystem.mapper.TeacherMapper;
import com.example.teachmanagesystem.service.ITeacherService;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {
    @Autowired
    TeacherMapper teacherMapper;


    @Override
    public Page<Teacher> findPages(Page<Teacher> page, Integer teacherId, String teacherName, String gender, String position, String departName) {
        return teacherMapper.findPages(page, teacherId, teacherName, gender, position, departName);
    }
}
