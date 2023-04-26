package com.example.teachmanagesystem.service.impl;

import com.example.teachmanagesystem.entity.OpenClass;
import com.example.teachmanagesystem.entity.SelectClass;
import com.example.teachmanagesystem.entity.Student;
import com.example.teachmanagesystem.mapper.SelectClassMapper;
import com.example.teachmanagesystem.service.ISelectClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    public boolean checkExist(String time, Integer studentId){
        List<String> times = selectClassMapper.getAllTime(studentId);
        for (String a : times) {

        }
        return true;
    }


    @Override
    public void studentSelect(Student student, List<OpenClass> openClasses) {
        SelectClass selectClass = new SelectClass();
        selectClass.setStudentId(student.getStudentId());
        String time;
        for (OpenClass openClass : openClasses) {
            time = openClass.getTime();
            if (checkExist(time, student.getStudentId())) {
                System.out.println("时间冲突!!");
                return;
            }
            selectClass.setOpenRecord(openClass.getRecordId());
        }

    }
}
