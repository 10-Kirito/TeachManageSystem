package com.example.teachmanagesystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.entity.OpenClass;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.teachmanagesystem.entity.Teacher;

import java.util.List;

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

    APIResponse<?> addOpenClass(Integer classRecord);

    Page<OpenClass> allOpenClass(Page<Object> page, String classId, String className, String departName);

    Page<Teacher> allAssignTeacher(Page<Object> page, String classId, String departName);

    Page<Teacher> allUnAssignTeacher(Page<Object> page, String classId, String departName);

    APIResponse<?> assignTeacher(Integer classRecord, Integer teacherId);

    APIResponse<?> cancelAssign(Integer classRecord, Integer teacherId);

    APIResponse<?> delOpenClass(Integer classRecord);

    APIResponse<?> updateExpansion(Integer recordId, Integer expansion);

    List<String> myClassName(Integer teacherId);

    List<OpenClass> myClassDetails(Integer teacherId);

    Page<OpenClass> myClassDetailsPages(Page<Object> page, Integer teacherId, String classId, String className);
}
