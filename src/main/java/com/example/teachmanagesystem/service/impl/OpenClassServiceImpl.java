package com.example.teachmanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.common.APIStatusCode;
import com.example.teachmanagesystem.entity.Class;
import com.example.teachmanagesystem.entity.OpenClass;
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
}
