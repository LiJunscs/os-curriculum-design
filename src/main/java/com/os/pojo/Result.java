package com.os.pojo;

/**
 * @author: lj
 * @desc
 * @create: 2024.03.03
 **/
public class Result {
    Integer code;
    String msg;
    Object data;

    // 构造函数
    public Result(Integer code, String msg) {
            this.code =code;
            this.msg =msg;
    }

    // 带参构造
    public Result(Integer code, String msg, Object data) {
            this.code =code;
            this.msg =msg;
            this.data =data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result success(String msg) {
        return new Result(1, msg);
    }

    public static Result success(String msg, Object data) {
        return new Result(1, msg, data);
    }

    public static Result fail(String msg, String errorInfo) {
        return new Result(0, "操作失败", errorInfo);
    }
}