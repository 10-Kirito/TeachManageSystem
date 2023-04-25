package com.example.teachmanagesystem.controller.login;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.common.APIStatusCode;
import com.example.teachmanagesystem.entity.Admins;
import com.example.teachmanagesystem.entity.login.AdminPasswords;
import com.example.teachmanagesystem.service.IAdminsService;
import com.example.teachmanagesystem.service.login.IAdminPasswordsService;
import com.example.teachmanagesystem.service.login.impl.AdminPasswordsServiceImpl;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kirito
 * @since 2023-04-18
 */
@RestController
@RequestMapping("/admin-passwords")
public class AdminPasswordsController {

    @Autowired
    IAdminPasswordsService adminPasswordsService;

    @Autowired
    IAdminsService adminsService;

    @GetMapping("/login")
    public APIResponse<Admins> login(@RequestParam String name,
                             @RequestParam String password){

        QueryWrapper<Admins> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_name", name);
        Admins admin = adminsService.getOne(queryWrapper);
        if(admin == null)
            return new APIResponse<Admins>(null, APIStatusCode.NOT_FOUND, "没有该用户!");
        else {
            QueryWrapper<AdminPasswords> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("admin_id", admin.getAdminId());
            queryWrapper1.eq("password_hash", password);
            AdminPasswords adminPasswords = adminPasswordsService.getOne(queryWrapper1);
            if (adminPasswords == null)
                return new APIResponse<Admins>(null, APIStatusCode.FORBIDDEN, "用户密码错误!");
            else
                return new APIResponse<Admins>(admin, APIStatusCode.SUCCESS, "登陆成功!");
        }
    }
}
