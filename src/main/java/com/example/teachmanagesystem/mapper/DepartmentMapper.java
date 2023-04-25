package com.example.teachmanagesystem.mapper;

import com.example.teachmanagesystem.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface DepartmentMapper extends BaseMapper<Department> {

    List<String> getAllName();
}
