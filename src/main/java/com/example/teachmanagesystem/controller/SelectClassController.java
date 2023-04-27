package com.example.teachmanagesystem.controller;


import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.common.APIStatusCode;
import com.example.teachmanagesystem.entity.OpenClass;
import com.example.teachmanagesystem.entity.SelectClass;
import com.example.teachmanagesystem.entity.Student;
import com.example.teachmanagesystem.service.ISelectClassService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.w3c.dom.ls.LSException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kirito
 * @since 2023-04-25
 */
@RestController
@RequestMapping("/select-class")
public class SelectClassController {

    @Autowired
    ISelectClassService iSelectClassService;

    @GetMapping("/student/allTime")
    public APIResponse<List<String>> selectAllTime(@RequestParam Integer studentId){

        List<String> allTime = iSelectClassService.selectAllTime(studentId);

        return  new APIResponse<>(allTime, APIStatusCode.SUCCESS, "获取数据成功");
    }


    /**
     * 选择课程接口，该接口的作用是返回指定学生的所有选择的课程!
     * @param studentId
     * @return
     */
    @GetMapping("/student/selected")
    public APIResponse<List<SelectClass>> selectMyClass(@RequestParam Integer studentId){
        List<SelectClass> selectClasses = iSelectClassService.selectMyClass(studentId);
        return new APIResponse<List<SelectClass>>(selectClasses, APIStatusCode.SUCCESS, "查询成功");
    }


    /**
     * 学生选课后端对应接口
     * 传进来的参数都是事先经过编码，所以需要先进行解码
     * @param encodeStudent(传入选课学生信息)
     * @param encodeData(传入该名学生选课信息,是一个列表)
     * @return
     * @throws UnsupportedEncodingException
     * @throws JsonProcessingException
     */
    @GetMapping("/student/select")
    public APIResponse<?> selectClass(@RequestParam String encodeStudent,
                                   @RequestParam String encodeData) throws UnsupportedEncodingException, JsonProcessingException {
        // 先进行相关的设置
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // 解码
        String decodedStudent = URLDecoder.decode(encodeStudent, "UTF-8");
        String decodedData = URLDecoder.decode(encodeData, "UTF-8");

        // 转换
        Student student = objectMapper.readValue(decodedStudent, Student.class);
        System.out.println(student);
//        List<OpenClass> openClasses = objectMapper.readValue(decodedData, new ArrayList<OpenClass>().getClass());
//        System.out.println(openClasses);
        // 我们获取json数据之后并且将其转换为list类型以后,我们是不能直接去使用list数据的,因为此时的list的类型是LinkedHashMap
        // 我们仍然需要进行一次转换,转换的过程中再将其转换为我们真正想要的数据类型
        List<OpenClass> openClasses = objectMapper.convertValue(objectMapper.readValue(decodedData, new ArrayList<OpenClass>().getClass()),new TypeReference<List<OpenClass>>() {});

        /**
         * 将处理好的学生信息以及对应学生的选课信息传入
         * 处理后的结果可能有以下几种：
         * 1. 选课失败！原因：选课时间冲突
         * 2. 选课成功！
         */
        return iSelectClassService.studentSelect(student, openClasses);
    }




}
