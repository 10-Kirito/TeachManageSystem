package com.example.teachmanagesystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.common.APIStatusCode;
import com.example.teachmanagesystem.entity.Class;
import com.example.teachmanagesystem.entity.OpenClass;
import com.example.teachmanagesystem.entity.Teacher;
import com.example.teachmanagesystem.mapper.OpenClassMapper;
import com.example.teachmanagesystem.service.IOpenClassService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    // 查询当前所有的开放的课程的信息
    // 返回的数据有：
    //{
    //    class_score: 4,
    //    class_id: "0830001",
    //    class_name: "离散数学",
    //    c.class_score: 4,
    //    capacity: 60,
    //    enrollment: 0,
    //    depart_ment: "计算机科学与工程学院"
    //}
    @GetMapping("/allOpenClass/pages")
    public Page<OpenClass> allOpenClass(@RequestParam(defaultValue = "1") Integer currentPage,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(required = false) String classId,
                                        @RequestParam(required = false) String className,
                                        @RequestParam(required = false) String departName){

        Page<OpenClass> classPage = iOpenClassService.allOpenClass(new Page<>(currentPage, pageSize), classId, className, departName);
        return classPage;
    }

    // 查询所有教授该门课程的老师信息，并且还要求是同一学院的老师
    @GetMapping("/allAssignTeacher")
    public Page<Teacher> allAssignTeacher(@RequestParam(defaultValue = "1") Integer currentPage,
                                          @RequestParam(defaultValue = "5") Integer pageSize,
                                          @RequestParam String  classId,
                                          @RequestParam String departName){
        Page<Teacher> teacherPage = iOpenClassService.allAssignTeacher(new Page<>(currentPage, pageSize),classId, departName);
        return teacherPage;
    }

    // 查询没有教授该门课程的老师信息，并且要求是同一学院的老师
    @GetMapping("/allUnAssignTeacher")
    public Page<Teacher> allUnAssignTeacher(@RequestParam(defaultValue = "1") Integer currentPage,
                                            @RequestParam(defaultValue = "5") Integer pageSize,
                                            @RequestParam String  classId,
                                            @RequestParam String departName){
        Page<Teacher> teacherPage = iOpenClassService.allUnAssignTeacher(new Page<>(currentPage, pageSize),classId, departName);
        return teacherPage;
    }


    // 查询指定老师教授的所有的课程名
    @GetMapping("/myClassName")
    public APIResponse<?> myClassName(@RequestParam Integer teacherId){

        List<String> classNames = iOpenClassService.myClassName(teacherId);

        return new APIResponse<>(classNames, APIStatusCode.SUCCESS, "查询成功");
    }

    // 查询当前教师教授的所有的课程的详细信息
    @GetMapping("/myClassDetails")
    public APIResponse<?> myClassDetails(@RequestParam Integer teacherId){

        List<OpenClass> openClasses = iOpenClassService.myClassDetails(teacherId);

        return new APIResponse<>(openClasses, APIStatusCode.SUCCESS, "查询成功");
    }

    // 查询当前教师教授的所有的课程的详细信息：分页查询
    @GetMapping("/myClassDetailsPages")
    public APIResponse<?> myClassDetailsPages(@RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "5") Integer pageSize,
                                              @RequestParam Integer teacherId,
                                              @RequestParam(required = false) String classId,
                                              @RequestParam(required = false) String className){
        Page<OpenClass> openClassPage = iOpenClassService.myClassDetailsPages(new Page<>(currentPage, pageSize), teacherId, classId, className);
        return new APIResponse<>(openClassPage, APIStatusCode.SUCCESS, "查询成功 ");
    }


    // -------------------------------------2. 删除-------------------------------
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





    // ----------------------------------------3. 添加------------------------
    // 根据什么进行添加，我们模拟的主要是一个老师可能针对一门课程开了多节课，这样同学们就可以选择不同时间段来上该老师的课程
    // 3.1 第一种添加，仅仅开设开门课程，但是并未分配该课程资源，比如说上课时间、上课地点、上课老师等等

    @GetMapping("/addOpenClass")
    public APIResponse<?> addOpenClass(@RequestParam Integer classRecord){
        return iOpenClassService.addOpenClass(classRecord);
    }

    // 给指定的课程分配新的老师:
    @GetMapping("/addOpenClass/assignTeacher")
    public APIResponse<?> assignTeacher(@RequestParam Integer classRecord,
                                        @RequestParam Integer teacherId){
        return iOpenClassService.assignTeacher(classRecord, teacherId);
    }

    // 取消分配指定的老师
    @GetMapping("/addOpenClass/cancelAssign")
    public APIResponse<?> cancelAssign(@RequestParam Integer classRecord,
                                       @RequestParam Integer teacherId){
        return iOpenClassService.cancelAssign(classRecord, teacherId);
    }

    // 取消开设本课程
    @GetMapping("/delOpenClass")
    public APIResponse<?> delOpenClass(@RequestParam Integer classRecord){
        return iOpenClassService.delOpenClass(classRecord);
    }



    // -----------------------------------更新数据------------------------------------------
    @GetMapping("/updateExpansion")
    public APIResponse<?> updateExpansion(@RequestParam Integer recordId,
                                          @RequestParam Integer expansion){
        return iOpenClassService.updateExpansion(recordId, expansion);
    }



    // 分配课程的上课时间和上课地点
    @GetMapping("/updateTime")
    public APIResponse<?> updateTime(@RequestParam Integer recordId,
                                     @RequestParam String time,
                                     @RequestParam String location){
        return iOpenClassService.updateTime(recordId, time, location);
    }

}
