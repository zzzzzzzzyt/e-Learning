package com.atguigu.servicebase.handler;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)//对controller生成的异常 进行捕获 返回其他视图
    @ResponseBody
    public R error(Exception exception) {
        exception.printStackTrace();
        return R.error().message("出现全局错误");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException arithmeticException) {
        arithmeticException.printStackTrace();
        return R.error().message("出现特定错误");
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException guliException) {
        guliException.printStackTrace();
        return R.error().message("出现了自定义错误");
    }
}
