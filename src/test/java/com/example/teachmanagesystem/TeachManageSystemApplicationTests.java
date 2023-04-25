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

}
