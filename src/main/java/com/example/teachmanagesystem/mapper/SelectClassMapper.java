package com.example.teachmanagesystem.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.entity.SelectClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.teachmanagesystem.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Kirito
 * @since 2023-04-25
 */
@Mapper
public interface SelectClassMapper extends BaseMapper<SelectClass> {
    List<String> getAllTime(Integer studentId);

    List<SelectClass> selectMyClass(Integer studentId);

    List<String> selectAllTime(Integer studentId);

    Page<Student> getClassStudents(Page<Object> page, Integer teacherId, String classId);

    List<Student> listClassStudents(Integer teacherId, String classId);

    Page<SelectClass> getStudentDetails(Page<Object> page, Integer studentId, String studentName, String className);
}
