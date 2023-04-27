package com.example.teachmanagesystem.service;

import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.entity.OpenClass;
import com.example.teachmanagesystem.entity.SelectClass;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.teachmanagesystem.entity.Student;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-25
 */
public interface ISelectClassService extends IService<SelectClass> {

    APIResponse<?> studentSelect(Student student, List<OpenClass> openClasses);

    List<SelectClass> selectMyClass(Integer studentId);

    List<String> selectAllTime(Integer studentId);

    APIResponse<?> dropClass(Student student, List<SelectClass> selectClasses);
}
