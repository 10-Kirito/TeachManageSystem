package com.example.teachmanagesystem.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Kirito
 * @since 2023-04-12
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    Page<Student> getAllStudents(Page<Object> page, Integer studentId, String studentName, String gender, String departName, String status);
}
