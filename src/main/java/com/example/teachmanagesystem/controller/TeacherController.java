package com.example.teachmanagesystem.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.entity.Teacher;
import com.example.teachmanagesystem.entity.TeacherDTO.TeacherDepartDTO;
import com.example.teachmanagesystem.mapper.TeacherMapper;
import com.example.teachmanagesystem.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
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
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    ITeacherService iTeacherService;

    @Autowired
    TeacherMapper teacherMapper;


    // --------------------------------查询------------------------------------
    // 1. 分页查询, 查询所有的数据：
    @GetMapping("/page")
    public Page<Teacher> selectByPage(@RequestParam(defaultValue = "1") Integer currentPage,
                                      @RequestParam(defaultValue = "2") Integer pageSize,
                                      @RequestParam(required = false) Integer teacherId,
                                      @RequestParam(required = false) String teacherName,
                                      @RequestParam(required = false) String gender,
                                      @RequestParam(required = false) String position,
                                      @RequestParam(required = false) Integer departId){

        Page<Teacher> page = new Page<>(currentPage, pageSize);

//        Page<Class> classPage = classMapper.selectPage(page, Wrappers.<Class>emptyWrapper());

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();

        if (teacherId != null){
            queryWrapper.like("teacher_id", "%" + teacherId + "%");
        }

        if (teacherName != null && !teacherName.isEmpty()){
            queryWrapper.like("teacher_name", "%" + teacherName + "%");
        }

        if (gender != null && !gender.isEmpty()){
            queryWrapper.eq("gender", gender);
        }

        if (position != null && !position.isEmpty()){
            queryWrapper.eq("position", position);
        }

        if (departId != null){
            queryWrapper.eq("depart_id", departId);
        }

        Page<Teacher> teacherPage = teacherMapper.selectPage(page, queryWrapper);
        return teacherPage;
    }

    // 联合查询下的分页查询，包括模糊查询
    @GetMapping("/pages")
    public Page<Teacher> selectByPage(@RequestParam(defaultValue = "1") Integer currentPage,
                                      @RequestParam(defaultValue = "2") Integer pageSize,
                                      @RequestParam(required = false) Integer teacherId,
                                      @RequestParam(required = false) String teacherName,
                                      @RequestParam(required = false) String gender,
                                      @RequestParam(required = false) String position,
                                      @RequestParam(required = false) String departName){


        Page<Teacher> teacherPage = iTeacherService.findPages(new Page<>(currentPage, pageSize), teacherId, teacherName, gender, position, departName);
        return teacherPage;
    }



    //    @GetMapping("lowlist")
    //    public List<Teacher> list(){
    //        return iTeacherService.list();
    //    }
    @GetMapping
    public List<Teacher> list_depart(){
        return teacherMapper.getTeacherDepartList();
    }

    // --------------------------------增加----------------------------------
    // 1. 默认的添加方法，以及除了主键的更新方法：
    @PostMapping
    public boolean save(@RequestBody Teacher teacher){
        return iTeacherService.saveOrUpdate(teacher);
    }


    /**
     * 身兼多职，可以添加老师也可以保存修改之后的老师的信息
     * @param teacher
     * @return
     */
    @PostMapping("add")
    public APIResponse<?> add(@RequestBody Teacher teacher){

        return iTeacherService.add(teacher);
    }
    // 2. 如果说你要修改主键的话，你需要另外的去调用Update方法
    // 如果说我们想要修改主键的值的话，我们需要删除原有的主键约束，并且添加新的主键约束
    // 而且需要更新与该记录相关的其他表的关联关系，最后将新主键的值更新到数据库当中；
    // 2023年4月14日19:16:56，暂时不去做这个功能
    // 2023年4月14日19:50:24，这个问题已经使用其他的办法进行解决，我额外的设置了一个主键用来唯一标识
    // FINISHED
    //@PostMapping("update")
    //public boolean update(@RequestBody Class class_){
    //    return iClassService.updateById(class_);
    //}


    // ------------------------------删除--------------------------------------
    // 1. 根据课程的主键进行删除
    @PostMapping("delete")
    public boolean deleteById(@RequestBody Teacher teacher){
        return iTeacherService.removeById(teacher);
    }

    // 2. 批量删除课程
    @PostMapping("muldelete")
    public boolean deleteMul(@RequestBody List<Teacher> teacherList){
        return iTeacherService.removeByIds(teacherList);
    }


    // -----------------------------导入和导出---------------------------------

    // 1. 导出
    @GetMapping("/exportAllTea")
    public void export(HttpServletResponse response) throws Exception{
        // 1.1 从数据库中查询所有的要导出的数据
        List<Teacher> classList = iTeacherService.list();

        // 1.2 自定义标题别名
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        excelWriter.addHeaderAlias("teacherId", "教师号");
        excelWriter.addHeaderAlias("teacherName", "教师名");
        excelWriter.addHeaderAlias("gender", "性别");
        excelWriter.addHeaderAlias("birthday", "出生年月");
        excelWriter.addHeaderAlias("position", "职称");
        excelWriter.addHeaderAlias("departName", "所属院系");

        // 1.3 写出对象到excelWriter中
        excelWriter.write(classList, true);

        // 1.4 设置浏览器相应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("教师信息","UTF-8");
        // 1.5 设置输出文件名称
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream servletOutputStream = response.getOutputStream();
        excelWriter.flush(servletOutputStream, true);
        servletOutputStream.close();
        excelWriter.close();
    }

    // 2. 导入
    @PostMapping("/importTeacher")
    public void importClass(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader excelReader = ExcelUtil.getReader(inputStream);
        excelReader.addHeaderAlias("教师号","teacherId");
        excelReader.addHeaderAlias("教师名","teacherName");
        excelReader.addHeaderAlias("性别","gender");
        excelReader.addHeaderAlias("出生年月", "birthday");
        excelReader.addHeaderAlias("职称","position");
        excelReader.addHeaderAlias("所属院系","departName");
        List<Teacher> teacherList = excelReader.readAll(Teacher.class);
        iTeacherService.saveBatch(teacherList);
    }


}
