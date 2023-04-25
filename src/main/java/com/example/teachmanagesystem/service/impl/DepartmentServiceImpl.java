package com.example.teachmanagesystem.service.impl;

import com.example.teachmanagesystem.entity.Department;
import com.example.teachmanagesystem.mapper.DepartmentMapper;
import com.example.teachmanagesystem.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-12
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public List<String> getAllName() {
        return departmentMapper.getAllName();
    }
}
