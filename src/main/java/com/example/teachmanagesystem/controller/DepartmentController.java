package com.example.teachmanagesystem.controller;


import com.example.teachmanagesystem.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kirito
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    IDepartmentService iDepartmentService;

    @GetMapping("/allName")
    public List<String> getAllName(){
        return iDepartmentService.getAllName();
    }

}
