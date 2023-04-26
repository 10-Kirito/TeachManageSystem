package com.example.teachmanagesystem;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.teachmanagesystem.controller.ClassController;
import com.example.teachmanagesystem.entity.Class;
import com.example.teachmanagesystem.mapper.ClassMapper;
import com.example.teachmanagesystem.service.IClassService;
import com.example.teachmanagesystem.service.impl.ClassServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jws.Oneway;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class TeachManageSystemApplicationTests {

    @Autowired
    ClassMapper classMapper;

    @Test
    void contextLoads() {
    }

    @Autowired
    ClassController controller;

    @Test
    public void selectByPage(){
        // 分页查询测试：baseMapper自带的分页查询
        Page<Class> page = new Page<>(1, 3);
        Page<Class> classPage = classMapper.selectPage(page, Wrappers.<Class>emptyWrapper());

        System.out.println(classPage.getTotal());
        List<Class> records = classPage.getRecords();
        for(Class class_ : records){
            System.out.println(class_.toString());
        }
    }

    @Test
    public void selectMutilPage(){
        controller.selectByPage(1, 2, "08302001", "",null, null, null);
    }

    @Test
    public void testEnum(){
        String time = "三1-5,一5-6,五7-8";
        String [] parts = time.split(",");
        for (String part : parts){
            String[] subparts = part.split("-");
            String start = subparts[0];
            String end = subparts[1];
            System.out.println("Start" + start.substring(0,1)+ ", end:" + end);
        }
    }

    @Test
    public void testCheckExist(){
        String time1 = "一1-4，二6-7"; // 第一时间段
        String time2 = "二8-10，三1-5"; // 第二时间段

        // 将时间段分割为子时间段
        String[] parts1 = time1.split("，");
        String[] parts2 = time2.split("，");



        // 将子时间段合并到一个列表中
        List<LocalTime[]> times = new ArrayList<>();
        for (String part1 : parts1) {
            LocalTime startTime1 = LocalTime.of(Integer.parseInt(part1.substring(1,2)), 0);
            LocalTime endTime1 = LocalTime.of(Integer.parseInt(part1.substring(3)), 0);
            times.add(new LocalTime[]{startTime1, endTime1});
            System.out.println(startTime1);
            System.out.println(endTime1);
        }
        for (String part2 : parts2) {
            LocalTime startTime2 = LocalTime.of(Integer.parseInt(part2.substring(1,2)), 0);
            LocalTime endTime2 = LocalTime.of(Integer.parseInt(part2.substring(3)), 0);
            times.add(new LocalTime[]{startTime2, endTime2});
            System.out.println(startTime2);
            System.out.println(endTime2);
        }

        // 检查时间段是否重叠
        boolean overlap = false;
        for (int i = 0; i < times.size(); i++) {
            for (int j = i + 1; j < times.size(); j++) {
                LocalTime startTime1 = times.get(i)[0];
                LocalTime endTime1 = times.get(i)[1];
                LocalTime startTime2 = times.get(j)[0];
                LocalTime endTime2 = times.get(j)[1];
                if (!(endTime1.isBefore(startTime2) || endTime2.isBefore(startTime1))) {
                    overlap = true;
                    break;
                }
            }
            if (overlap) {
                break;
            }
        }

        // 打印结果
        if (overlap) {
            System.out.println("时间段重叠");
        } else {
            System.out.println("时间段不重叠");
        }
    }

}
