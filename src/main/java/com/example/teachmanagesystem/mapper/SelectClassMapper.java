package com.example.teachmanagesystem.mapper;

import com.example.teachmanagesystem.entity.SelectClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
