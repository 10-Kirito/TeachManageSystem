package com.example.teachmanagesystem.service.login.impl;

import com.example.teachmanagesystem.entity.login.TeacherPasswords;
import com.example.teachmanagesystem.mapper.login.TeacherPasswordsMapper;
import com.example.teachmanagesystem.service.login.ITeacherPasswordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-18
 */
@Service
public class TeacherPasswordsServiceImpl extends ServiceImpl<TeacherPasswordsMapper, TeacherPasswords> implements ITeacherPasswordsService {

}
