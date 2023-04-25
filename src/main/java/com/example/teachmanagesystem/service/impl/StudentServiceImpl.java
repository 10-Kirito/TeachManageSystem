package com.example.teachmanagesystem.service.impl;

import com.example.teachmanagesystem.entity.Student;
import com.example.teachmanagesystem.mapper.StudentMapper;
import com.example.teachmanagesystem.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-12
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
