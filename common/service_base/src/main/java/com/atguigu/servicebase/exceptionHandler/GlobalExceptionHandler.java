package com.atguigu.servicebase.exceptionHandler;

import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j //用到logback日志
public class GlobalExceptionHandler {
    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)//只要是异常就走以下方法（全局异常处理）
    @ResponseBody //为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("全局异常处理");
    }
    //特定异常
    @ExceptionHandler(ArithmeticException.class)//
    @ResponseBody //为了返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行ArithmeticException异常处理");
    }

    //自定义异常
    @ExceptionHandler(GujtException.class)//
    @ResponseBody //为了返回数据
    public R error(GujtException e){
        log.error(ExceptionUtil.getMessage(e)); //异常输出语句，把异常出力到log文件中
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
