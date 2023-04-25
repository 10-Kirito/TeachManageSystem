package com.example.teachmanagesystem.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.entity.OpenClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
}
