package com.example.teachmanagesystem.service.login.impl;

import com.example.teachmanagesystem.entity.login.AdminPasswords;
import com.example.teachmanagesystem.mapper.login.AdminPasswordsMapper;
import com.example.teachmanagesystem.service.login.IAdminPasswordsService;
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
public class AdminPasswordsServiceImpl extends ServiceImpl<AdminPasswordsMapper, AdminPasswords> implements IAdminPasswordsService {

}
