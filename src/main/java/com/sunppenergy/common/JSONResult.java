package com.sunppenergy.common;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 *  Created by momo on 2016/8/17.
 * ajax接口返回数据格式
 * */
@ApiModel(value = "返回类")
public final class JSONResult<T> {
    //    @ApiModelProperty(value = "code")
//    private int code;
//    @ApiModelProperty(value = "返回信息")
//    private String msg;
//    @ApiModelProperty(value = "对象")
////    private Object result;
//    private T result;
//    @ApiModelProperty(value = "对象")
//    private Object page;
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(ErrorCode errorCode) {
//        this.code = errorCode.getCode();
//        this.msg = errorCode.getMsg();
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public T getResult() {
//        return result;
//    }
//
//    public void setResult(T result) {
//        this.result = result;
//    }
//
//    public Object getPage() {
//        return page;
//    }
//
//    public void setPage(Object page) {
//        this.page = page;
//    }
//
//    @Override
//    public String toString() {
//        return JSONObject.toJSONString(this);
//    }
    @ApiModelProperty(value = "code")
    private int code;
    @ApiModelProperty(value = "msg")
    private String msg;
    @ApiModelProperty(value = "data")
    private T data;
    @ApiModelProperty(value = "page")
    private Object page;

    public JSONResult() {
    }

    public JSONResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JSONResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JSONResult(int code, String msg, T data, Object page) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.page = page;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }
}

