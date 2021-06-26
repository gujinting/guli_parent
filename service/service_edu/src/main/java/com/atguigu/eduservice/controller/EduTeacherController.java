package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public R findAllTeacher(){
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = teacherService.list(null);

        return R.ok().data("items",list);
    }
    //2.逻辑删除讲师的方法
    /*
    * 如何测试
    * 借助工具测试：
    *   swagger测试 http://localhost:8001/swagger-ui.html
    *       1.生成接口文档
    *       2.接口测试
    * 创建公共模块，整合swagger，为了所有模块都能进行使用
    *   postman
    * */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")//需要通过url传递参数
    //@PathVariable String id 获取到url中的参数值
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                                     @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //3 分页查询讲师的方法
    @ApiOperation(value = "分页查询讲师的方法")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name = "current", value = "当前页", required = true)
                                 @PathVariable long current,
                             @ApiParam(name = "limit", value = "页数", required = true)
                             @PathVariable long limit){

        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //调用方法时候，底层封装，把分页的所用数据封装到了pageTeacher的对象中
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records =  pageTeacher.getRecords();

/*//两种方法都可以
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        R.ok().data(map);
*/
        return R.ok().data("total",total).data("rows",records);
    }

/*
* 第一步 把条件值传递到接口里面
*   把条件值封装到对象里面，把对象传递到接口里面
* */

    //4条件查询带分页的方法
    @ApiOperation(value = "分页查询讲师的方法")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageListTeacherCondition(@ApiParam(name = "current", value = "当前页", required = true)
                             @PathVariable long current,
                                      @ApiParam(name = "limit", value = "页数", required = true)
                             @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery){

        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();


        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //wrapper条件判断
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("begin",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("end",end);
        }

        //调用方法时候，底层封装，把分页的所用数据封装到了pageTeacher的对象中
        teacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records =  pageTeacher.getRecords();

/*//两种方法都可以
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        R.ok().data(map);
*/
        return R.ok().data("total",total).data("rows",records);
    }
    //添加讲师的接口方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }

    }

    /*讲师修改功能
    *   1，根据讲师ID查询
    *   2，讲师的修改
    * */
    //根据讲师ID查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //讲师的修改功能
    @PostMapping ("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }

    }



}

