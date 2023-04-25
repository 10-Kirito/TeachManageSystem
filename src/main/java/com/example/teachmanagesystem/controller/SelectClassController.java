package com.example.teachmanagesystem.controller;


import com.example.teachmanagesystem.entity.OpenClass;
import com.example.teachmanagesystem.entity.SelectClass;
import com.example.teachmanagesystem.entity.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.List;

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

    @GetMapping("/student/select")
    public int selectClass(@RequestParam Integer test,
                           @RequestParam String encodeStudent) throws UnsupportedEncodingException, JsonProcessingException {
        String decodedStudent = URLDecoder.decode(encodeStudent, "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Student student = objectMapper.readValue(decodedStudent, Student.class);
        System.out.println(student);
        return 1;
    }




}
