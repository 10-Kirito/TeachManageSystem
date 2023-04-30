package com.example.teachmanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.common.APIResponse;
import com.example.teachmanagesystem.common.APIStatusCode;
import com.example.teachmanagesystem.entity.OpenClass;
import com.example.teachmanagesystem.entity.SelectClass;
import com.example.teachmanagesystem.entity.Student;
import com.example.teachmanagesystem.mapper.SelectClassMapper;
import com.example.teachmanagesystem.service.ISelectClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.teachmanagesystem.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kirito
 * @since 2023-04-25
 */
@Service
public class SelectClassServiceImpl extends ServiceImpl<SelectClassMapper, SelectClass> implements ISelectClassService {
    @Autowired
    SelectClassMapper selectClassMapper;



    // -------------------------------------学生选课----------------------------------------
    /**
     * 比较选择的课程的上课时间段与该名学生已经选择的课程的时间段是否冲突!
     * @param time
     * @param studentId
     * @return 如果冲突的话就会返回true, 不冲突的话就会返回false
     */
    public boolean checkExist(String time, Integer studentId){
        List<String> times = selectClassMapper.getAllTime(studentId);
        for (String temp : times) {
            // 一旦发现有时间段冲突,就会返回true
            if (Tools.checkExistFinally(time, temp))
                return true;
        }
        // 如果没有时间段冲突的话,就返回false
        return false;
    }

    /**
     * 该函数的功能说就是处理学生选课！
     * 逻辑很简单就是先检查所选择的课程是否有时间冲突，如果有的话就会终止选课，提示学生时间冲突;
     * 如果没有选课冲突的话，就将学生选择的课程一个一个的插入到选课表当中!
     * @param student(传入选课学生的相关信息,以防止用得到)
     * @param openClasses(传入选课学生选课信息,是一个列表,里面是选择的课程)
     */
    @Override
    public APIResponse<?> studentSelect(Student student, List<OpenClass> openClasses) {

        System.out.println(openClasses);
        SelectClass selectClass = new SelectClass();
        selectClass.setStudentId(student.getStudentId());

        String time;

        // 这里先检测是否存在选课时间冲突:
        //      如果说存在冲突的话,停止本次选课;
        //      如果说没有时间冲突的话，就将相应的数据插入到数据库当中;
        System.out.println(openClasses);

        for (OpenClass openClass: openClasses) {
            time = openClass.getTime();
            if (checkExist(time, student.getStudentId())) {
                System.out.println("时间冲突!!");
                return new APIResponse<>(null, APIStatusCode.BAD_REQUEST, "选课时间冲突!");
            }
        }
        // 进行数据库中的数据的更新:
        for (OpenClass openClass : openClasses){
            selectClass.setOpenRecord(openClass.getRecordId());
            // 进行保存，这里没有做异常管理
            save(selectClass);
            System.out.println("选课数据保存成功!");
        }
        return new APIResponse<>(null, APIStatusCode.SUCCESS, "选课成功!");
    }

    @Override
    public List<SelectClass> selectMyClass(Integer studentId) {
        List<SelectClass> selectClasses = selectClassMapper.selectMyClass(studentId);
        return selectClasses;
    }

    @Override
    public List<String> selectAllTime(Integer studentId) {

        return selectClassMapper.selectAllTime(studentId);
    }

    @Override
    public APIResponse<?> dropClass(Student student, List<SelectClass> selectClasses) {
        QueryWrapper<SelectClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", student.getStudentId());
        for (SelectClass selectClass: selectClasses) {
            selectClass.setStudentId(student.getStudentId());
            queryWrapper.eq("open_record", selectClass.getOpenRecord());
            remove(queryWrapper);
        }
        return new APIResponse<>(null, APIStatusCode.SUCCESS, "删除成功!");
    }

    @Override
    public Page<Student> getClassStudents(Page<Object> page, Integer teacherId, String classId) {

        return selectClassMapper.getClassStudents(page, teacherId, classId);
    }

    @Override
    public List<Student> listClassStudents(Integer teacherId, String classId) {

        return selectClassMapper.listClassStudents(teacherId, classId);
    }

    @Override
    public Page<SelectClass> getStudentDetails(Page<Object> page, Integer studentId, String studentName, String className) {
        return selectClassMapper.getStudentDetails(page, studentId, studentName, className);
    }

    @Override
    public APIResponse<?> regist(Integer recordId, Integer usuallyScore, Integer testScore) {
        SelectClass selectClass = getById(recordId);
        selectClass.setUsuallyScore(usuallyScore);
        selectClass.setTestScore(testScore);
        return new APIResponse<>( updateById(selectClass), APIStatusCode.SUCCESS, "登记成功");
    }

    // -------------------------------------学生选课END-------------------------------------
}
