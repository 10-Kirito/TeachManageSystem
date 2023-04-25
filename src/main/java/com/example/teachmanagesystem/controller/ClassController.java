package com.example.teachmanagesystem.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.entity.Class;
import com.example.teachmanagesystem.entity.Department;
import com.example.teachmanagesystem.entity.Teacher;
import com.example.teachmanagesystem.mapper.ClassMapper;
import com.example.teachmanagesystem.mapper.DepartmentMapper;
import com.example.teachmanagesystem.service.IClassService;
import com.example.teachmanagesystem.service.IDepartmentService;
import com.example.teachmanagesystem.service.impl.ClassServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLDecoder;
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
@RequestMapping("/class")
public class ClassController {
    @Autowired
    IClassService iClassService;
    @Autowired
    ClassMapper classMapper;
    @Autowired
    IDepartmentService iDepartmentService;

    // --------------------------------查询------------------------------------
    // 1. 分页查询, 查询所有的数据：
    // 该查询没有做多表联，被弃用
    //@GetMapping("/page")
    public Page<Class> selectByPageDeprecated(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "2") Integer pageSize,
                                    @RequestParam(required = false) String classId,
                                    @RequestParam(required = false) String className,
                                    @RequestParam(required = false) Integer classScore,
                                    @RequestParam(required = false) Integer classTime,
                                    @RequestParam(required = false) Integer departId){

        Page<Class> page = new Page<>(currentPage, pageSize);

//        Page<Class> classPage = classMapper.selectPage(page, Wrappers.<Class>emptyWrapper());

        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();

        if (classId != null && !classId.isEmpty()){
            queryWrapper.like("class_id", "%" + classId + "%");
        }

        if (className != null && !className.isEmpty()){
            queryWrapper.like("class_name", "%" + className + "%");
        }

        if (classScore != null){
            queryWrapper.eq("class_score", classScore);
        }

        if (classTime != null){
            queryWrapper.eq("class_time", classTime);
        }

        if (departId != null){
            queryWrapper.eq("depart_id", departId);
        }

        Page<Class> classPage = classMapper.selectPage(page, queryWrapper);
        return classPage;
    }
    @GetMapping("/page")
    public Page<Class> selectByPage(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "2") Integer pageSize,
                                    @RequestParam(required = false) String classId,
                                    @RequestParam(required = false) String className,
                                    @RequestParam(required = false) Integer classScore,
                                    @RequestParam(required = false) Integer classTime,
                                    @RequestParam(required = false) String departName){

        Page<Class> classPage = iClassService.findPages(new Page<>(currentPage, pageSize), classId, className, classScore, classTime, departName);
        return classPage;
    }



    @GetMapping
    public List<Class> list(){
        return iClassService.list();
    }



    // --------------------------------增加----------------------------------
    // 1. 默认的添加方法，以及除了主键的更新方法：
    @PostMapping
    public boolean save(@RequestBody Class class_){
        // 由于前端传来的数据是多表联接之后的，所以说我们首先进行转换
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("depart_name", class_.getDepartName());
        Department department = iDepartmentService.getOne(queryWrapper);
        class_.setDepartId(department.getDepartId());

        return iClassService.saveOrUpdate(class_);
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
    public boolean deleteById(@RequestBody Class class_){
        return iClassService.removeById(class_);
    }

    // 2. 批量删除课程
    @PostMapping("muldelete")
    public boolean deleteMul(@RequestBody List<Class> classList){
        return iClassService.removeByIds(classList);
    }


    // -----------------------------导入和导出---------------------------------

    // 1. 导出
    @GetMapping("/exportAllClass")
    public void export(HttpServletResponse response) throws Exception{
        // 1.1 从数据库中查询所有的要导出的数据
        List<Class> classList = iClassService.listwithDepart();

        // 1.2 自定义标题别名
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        excelWriter.addHeaderAlias("classId", "课程号");
        excelWriter.addHeaderAlias("className", "课程名");
        excelWriter.addHeaderAlias("classScore", "课程分数");
        excelWriter.addHeaderAlias("classTime", "课时");
        excelWriter.addHeaderAlias("departName", "所属院系");

        // 1.3 写出对象到excelWriter中
        excelWriter.write(classList, true);

        // 1.4 设置浏览器相应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("课程信息","UTF-8");
        // 1.5 设置输出文件名称
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream servletOutputStream = response.getOutputStream();
        excelWriter.flush(servletOutputStream, true);
        servletOutputStream.close();
        excelWriter.close();
    }

    // 2. 导入
    @PostMapping("/importClass")
    public void importClass(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader excelReader = ExcelUtil.getReader(inputStream);
        excelReader.addHeaderAlias("课程号","classId");
        excelReader.addHeaderAlias("课程名","className");
        excelReader.addHeaderAlias("课程分数","classScore");
        excelReader.addHeaderAlias("课时", "classTime");
        excelReader.addHeaderAlias("所属院系","departId");
        List<Class> classList = excelReader.readAll(Class.class);
        iClassService.saveBatch(classList);
    }
}
