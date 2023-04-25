package com.example.teachmanagesystem.service;

import com.example.teachmanagesystem.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-12
 */
public interface IDepartmentService extends IService<Department> {

    List<String> getAllName();
}
