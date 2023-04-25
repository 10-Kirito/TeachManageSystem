package com.example.teachmanagesystem.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.entity.Class;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.teachmanagesystem.entity.TeacherDTO.TeacherDepartDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

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
public interface ClassMapper extends BaseMapper<Class> {

    Page<Class> findPage(Page<Class> page,
                         String classId,
                         String className,
                         Integer classScore,
                         Integer classTime,
                         String departName);

    List<Class> listwithDepart();
}
