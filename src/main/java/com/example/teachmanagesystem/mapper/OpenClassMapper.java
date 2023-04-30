package com.example.teachmanagesystem.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.entity.OpenClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.teachmanagesystem.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Kirito
 * @since 2023-04-19
 */
@Mapper
public interface OpenClassMapper extends BaseMapper<OpenClass> {

    Page<OpenClass> findPages(Page<OpenClass> page, String classId, String className, String teacherName, Integer classScore, String departName);

    Page<OpenClass> allOpenClass(Page<Object> page, String classId, String className, String departName);

    Page<Teacher> allAssignTeacher(Page<Object> page, String classId, String departName);

    Page<Teacher> allUnAssignTeacher(Page<Object> page, String classId, String departName);

    List<String> myClassName(Integer teacherId);

    List<OpenClass> myClassDetails(Integer teacherId);

    Page<OpenClass> myClassDetailsPages(Page<Object> page, Integer teacherId, String classId, String className);
}
