package com.example.teachmanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.common.APIStatusCode;
import com.example.teachmanagesystem.entity.Class;
import com.example.teachmanagesystem.entity.OpenClass;
import com.example.teachmanagesystem.entity.Teacher;
import com.example.teachmanagesystem.mapper.ClassMapper;
import com.example.teachmanagesystem.mapper.OpenClassMapper;
import com.example.teachmanagesystem.service.IOpenClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-19
 */
@Service
public class OpenClassServiceImpl extends ServiceImpl<OpenClassMapper, OpenClass> implements IOpenClassService {
    @Autowired
    OpenClassMapper openClassMapper;

    @Autowired
    ClassMapper classMapper;

    @Override
    public Page<OpenClass> findPage(Page<OpenClass> page, String classId, String className, String teacherName, Integer classScore, String departName) {
        return openClassMapper.findPages(page, classId, className, teacherName, classScore, departName);
    }

    @Override
    public APIResponse<?> delete(String classId, Integer teacherId, String time) {
        QueryWrapper<Class> classQueryWrapper = new QueryWrapper<>();
        classQueryWrapper.eq("class_id", classId);
        Class class_= classMapper.selectOne(classQueryWrapper);
        if (class_ == null)
            return new APIResponse<>(null, APIStatusCode.NOT_FOUND, "该课程已经不存在！");

        QueryWrapper<OpenClass> classQueryWrapper1 = new QueryWrapper<>();
        classQueryWrapper1.eq("id", class_.getId());
        classQueryWrapper1.eq("teacher_id", teacherId);
        classQueryWrapper1.eq("time", time);


        return null;
    }

    @Override
    public APIResponse<?> addOpenClass(Integer classRecord) {
        OpenClass openClass = new OpenClass();
        openClass.setClassRecord(classRecord);
        openClass.setCapacity(60);
        openClass.setEnrollment(0);

        // 检测该门课程是否已经开放，如果已经开放的话，就提示该门课程已经开放，不可以重复开放
        QueryWrapper<OpenClass> queryWrapper = new QueryWrapper<>();
        System.out.println(queryWrapper.eq("class_record", classRecord));
        if (getOne(queryWrapper) != null)
            return new APIResponse<>(null, APIStatusCode.BAD_REQUEST, "该课程已经开放!");
        save(openClass);
        return new APIResponse<>(null, APIStatusCode.SUCCESS, "开放课程成功!");
    }

    @Override
    public Page<OpenClass> allOpenClass(Page<Object> page, String classId, String className, String departName) {

        return openClassMapper.allOpenClass(page, classId, className, departName);
    }

    @Override
    public Page<Teacher> allAssignTeacher(Page<Object> page, String classId, String departName) {
        return openClassMapper.allAssignTeacher(page, classId, departName);
    }

    @Override
    public Page<Teacher> allUnAssignTeacher(Page<Object> page, String classId, String departName) {
        return openClassMapper.allUnAssignTeacher(page, classId, departName);
    }
}
