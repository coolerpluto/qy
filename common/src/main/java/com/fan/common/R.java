package com.fan.common;

import com.fan.constants.result.ResultConst;
import lombok.Data;

@Data
public class R<T>{
    private Integer code;
    private String status;
    private String message;
    private T data;

    private R(){}

    private R(Integer code, String status, String message){
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public static R ok(){
        R r = new R(ResultConst.SUCCESS_CODE, ResultConst.SUCCESS_STATUS, "成功");
        return r;
    }

    public static R fail(){
        R r = new R(ResultConst.FAILED_CODE, ResultConst.SUCCESS_STATUS, "失败");
        return r;
    }

    public R message(String message){
        this.message = message;
        return this;
    }

    public R<T> data(T data){
        this.data = data;
        return this;
    }
}
