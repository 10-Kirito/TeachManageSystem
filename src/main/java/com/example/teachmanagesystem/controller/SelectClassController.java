package com.example.teachmanagesystem.controller;


import com.example.teachmanagesystem.entity.OpenClass;
import com.example.teachmanagesystem.entity.SelectClass;
import com.example.teachmanagesystem.entity.Student;
import com.example.teachmanagesystem.service.ISelectClassService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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


    @GetMapping("/student/select")
    public int selectClass(@RequestParam String encodeStudent,
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
        List<OpenClass> openClasses = objectMapper.readValue(decodedData, new ArrayList<>().getClass());
        System.out.println(openClasses);

        // 交由给Service来做下一步的事情
        iSelectClassService.studentSelect(student, openClasses);
        return 1;
    }




}
