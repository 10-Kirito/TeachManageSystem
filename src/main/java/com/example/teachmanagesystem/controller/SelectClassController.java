package com.example.teachmanagesystem.controller;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import java.util.Currency;
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

    @Autowired
    ISelectClassService iSelectClassService;


    /**
     * 获取所有的已选课程的上课时间
     * @param studentId
     * @return
     */
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
        // List<OpenClass> openClasses = objectMapper.readValue(decodedData, new ArrayList<OpenClass>().getClass());
        // System.out.println(openClasses);
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


    @GetMapping("/student/delete")
    public APIResponse<?> deleteClass(@RequestParam String encodeStudent,
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
        // List<OpenClass> openClasses = objectMapper.readValue(decodedData, new ArrayList<OpenClass>().getClass());
        // System.out.println(openClasses);
        // 我们获取json数据之后并且将其转换为list类型以后,我们是不能直接去使用list数据的,因为此时的list的类型是LinkedHashMap
        // 我们仍然需要进行一次转换,转换的过程中再将其转换为我们真正想要的数据类型
        List<SelectClass> selectClasses = objectMapper.convertValue(objectMapper.readValue(decodedData, new ArrayList<SelectClass>().getClass()),new TypeReference<List<SelectClass>>() {});

        /**
         * 将处理好的学生信息以及对应学生的选课信息传入
         * 处理后的结果只能有一种成功
         * 1. 退课成功!
         */
        return iSelectClassService.dropClass(student, selectClasses);
    }




    // 教师端所需要的接口:
    // 1. 查询选择某一节课的所有的学生信息
    @GetMapping("/getClassStudents")
    public APIResponse<?> getClassStudents(@RequestParam(defaultValue = "1") Integer currentPage,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam Integer teacherId,
                                           @RequestParam String classId){

        Page<Student> page = iSelectClassService.getClassStudents(new Page<>(currentPage, pageSize), teacherId, classId);
        return new APIResponse<>(page, APIStatusCode.SUCCESS, "查询成功");
    }



    // 2. 导出选择某一节课的所有的学生信息,不涉及成绩：
    @GetMapping("/exportClassStudents")
    public void exportClassStudents(HttpServletResponse response,
                                    @RequestParam Integer teacherId,
                                    @RequestParam String classId) throws IOException {
        List<Student> students = iSelectClassService.listClassStudents(teacherId, classId);
        // 1.2 自定义标题别名
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        excelWriter.addHeaderAlias("studentId", "学号");
        excelWriter.addHeaderAlias("studentName", "姓名");
        excelWriter.addHeaderAlias("gender", "性别");
        excelWriter.addHeaderAlias("nativePlace", "籍贯");
        excelWriter.addHeaderAlias("phoneNumber", "电话号码");
        excelWriter.addHeaderAlias("departName", "所属院系");
        excelWriter.addHeaderAlias("state", "状态");

        excelWriter.write(students, true);

        // 1.4 设置浏览器相应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("学生信息","UTF-8");
        // 1.5 设置输出文件名称
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream servletOutputStream = response.getOutputStream();
        excelWriter.flush(servletOutputStream, true);
        servletOutputStream.close();
        excelWriter.close();
    }

    // 3. 获取该名教师所教授的所有学生信息，进行登记分数操作:

    @GetMapping("/registStudents")
    public APIResponse<?> getStudentDetails(@RequestParam(defaultValue = "1") Integer currentPage,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(required = false) Integer studentId,
                                            @RequestParam(required = false) String studentName,
                                            @RequestParam(required = false) String className){
        Page<SelectClass> page = iSelectClassService.getStudentDetails(new Page<>(currentPage, pageSize), studentId, studentName, className);
        return new APIResponse<>(page, APIStatusCode.SUCCESS, "查询成功!");
    }

    // 4. 登记分数
    @GetMapping("/registScore")
    public APIResponse<?> registScore(@RequestParam Integer recordId,
                                      @RequestParam Integer usuallyScore,
                                      @RequestParam Integer testScore){
        return iSelectClassService.regist(recordId, usuallyScore, testScore);
    }

}
