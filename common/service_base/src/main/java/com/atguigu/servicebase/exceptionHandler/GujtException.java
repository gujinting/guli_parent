package com.atguigu.servicebase.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //生成get set 方法
@AllArgsConstructor //生成有参数的构造方法
@NoArgsConstructor //生成无参构造的构造方法
public class GujtException extends RuntimeException {

    private Integer code; //状态码

    private String msg; //异常信息


    @Override
    public String toString() {
        return "GujtException{" +
        "message=" + this.getMessage() +
        ", code=" + code +
        '}';
    }
}
