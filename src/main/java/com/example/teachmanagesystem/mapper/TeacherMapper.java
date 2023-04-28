package com.example.teachmanagesystem.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.teachmanagesystem.entity.TeacherDTO.TeacherDepartDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Kirito
 * @since 2023-04-12
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    List<Teacher> getTeacherDepartList();


    Page<Teacher> findPages(Page<Teacher> page,
                            Integer teacherId,
                            String teacherName,
                            String gender,
                            String position,
                            String departName);
}
