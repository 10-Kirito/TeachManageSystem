package com.example.teachmanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.entity.Class;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-12
 */
public interface IClassService extends IService<Class> {
    Page<Class> findPages(Page<Class> page, String classId, String className, Integer classScore, Integer classTime, String departName);

    List<Class> listwithDepart();
}
