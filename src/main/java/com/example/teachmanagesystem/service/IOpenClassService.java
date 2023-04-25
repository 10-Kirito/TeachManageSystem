package com.example.teachmanagesystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.entity.OpenClass;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-19
 */
public interface IOpenClassService extends IService<OpenClass> {

    Page<OpenClass> findPage(Page<OpenClass> page, String classId, String className, String teacherName, Integer classScore, String departName);

    APIResponse<?> delete(String classId, Integer teacherId, String time);
}
