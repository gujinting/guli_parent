package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-06-18
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //访问地址：http://localhost:8001/eduservice/teacher/findAll
    //把service注入
    @Autowired
    private EduTeacherService teacherService;
    //1.查询讲师表所有的数据
    //rest风格REST是一种软件架构风格，或者说是一种规范，其强调HTTP应当以资源为中心，并且规范了URI的风格；规范了HTTP请求动作（GET/PUT/POST/DELETE/HEAD/OPTIONS）的使用，具有对应的语义。
    /*
    * rest风格：
    * REST是一种软件架构风格，或者说是一种规范，其强调HTTP应当以资源为中心，
    * 并且规范了URI的风格；规范了HTTP请求动作（GET/PUT/POST/DELETE/HEAD/OPTIONS）的使用，具有对应的语义。
    * */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public List<EduTeacher> findAllTeacher(){
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = teacherService.list(null);

        return list;
    }
    //2.逻辑删除讲师的方法
    /*
    * 如何测试
    * 借助工具测试：
    *   swagger测试
    *       1.生成接口文档
    *       2.接口测试
    * 创建公共模块，整合swagger，为了所有模块都能进行使用
    *   postman
    * */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")//需要通过url传递参数
    //@PathVariable String id 获取到url中的参数值
    public boolean removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                                     @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        return flag;
    }
}

