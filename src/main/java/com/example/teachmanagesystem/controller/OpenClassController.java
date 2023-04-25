package com.example.teachmanagesystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.entity.Class;
import com.example.teachmanagesystem.entity.OpenClass;
import com.example.teachmanagesystem.mapper.OpenClassMapper;
import com.example.teachmanagesystem.service.IOpenClassService;
import io.swagger.models.auth.In;
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
 * @since 2023-04-19
 */
@RestController
@RequestMapping("/open-class")
public class OpenClassController {
    @Autowired
    IOpenClassService iOpenClassService;

    @Autowired
    OpenClassMapper openClassMapper;


    // -----------------------1. 查询------------------------

    // ------------------分页查询,查询结果为：------------------
    // {
    //            "id": 6,
    //            "teacherId": 1001,
    //            "capacity": 60,
    //            "enrollment": 0,
    //            "time": "一5-6，三5-6，五7-8",
    //            "location": "计308",
    //            "term": "23年春季",
    //            "classId": "08305001",
    //            "className": "离散数学",
    //            "teacherName": "陈迪茂",
    //            "position": "副教授",
    //            "score":"4"
    // }
    @GetMapping("/pages")
    public Page<OpenClass> selectByPage(@RequestParam(defaultValue = "1") Integer currentPage,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(required = false) String classId,
                                        @RequestParam(required = false) String className,
                                        @RequestParam(required = false) String teacherName,
                                        @RequestParam(required = false) Integer classScore,
                                        @RequestParam(required = false) String departName) {
        Page<OpenClass> classPage = iOpenClassService.findPage(new Page<>(currentPage, pageSize), classId, className, teacherName, classScore, departName);
        return classPage;
    }


    // -----------------------2. 删除------------------------
    /**
     * Params:
     * @classId;
     * @teacherId;
     * @time;
     */
    public APIResponse<?> delete(@RequestParam String classId,
                                 @RequestParam Integer teacherId,
                                 @RequestParam String time){
        return iOpenClassService.delete(classId, teacherId, time);
    }





    // -----------------------3. 添加------------------------
    // 根据什么进行添加，我们模拟的主要是一个老师可能针对一门课程开了多节课，这样同学们就可以选择不同时间段来上该老师的课程
    /**
     * Params:
     * @classId;
     * @teacherId;
     * @time;
     */



}
