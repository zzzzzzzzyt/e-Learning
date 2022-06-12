package com.atguigu.commonutils;

import lombok.Data;

import java.util.HashMap;

@Data
public class R {
    private boolean success;
    private Integer code;
    private String message;
    private HashMap<String, Object> data = new HashMap<>();

    private R() {
    }



    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }


    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(HashMap<String, Object> map) {
        this.setData(map);
        return this;
    }

}
