package com.atguigu.commonutils.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor 生成全参构造方法
//@NoArgsConstructor  生成无参构造方法
public class GuliException extends RuntimeException{

    private Integer code;//状态码

    private String msg;//异常信息

    public GuliException() {
    }

    public GuliException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
