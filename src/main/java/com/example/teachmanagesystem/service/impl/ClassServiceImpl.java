package com.example.teachmanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.entity.Class;
import com.example.teachmanagesystem.mapper.ClassMapper;
import com.example.teachmanagesystem.service.IClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-12
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements IClassService {
    @Autowired
    ClassMapper classMapper;


    @Override
    public Page<Class> findPages(Page<Class> page, String classId, String className, Integer classScore, Integer classTime, String departName) {
        return classMapper.findPage(page, classId, className, classScore, classTime, departName);
    }

    @Override
    public List<Class> listwithDepart() {
        return classMapper.listwithDepart();
    }
}
