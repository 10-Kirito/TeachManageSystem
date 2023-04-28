package com.example.teachmanagesystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-12
 */
public interface ITeacherService extends IService<Teacher> {

    Page<Teacher> findPages(Page<Teacher> objectPage, Integer teacherId, String teacherName, String gender, String position, String departName);

    APIResponse<?> add(Teacher teacher);
}
