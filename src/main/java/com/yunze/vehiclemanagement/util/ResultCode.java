package com.yunze.vehiclemanagement.util;

import lombok.Data;

@Data
public class ResultCode<T> {
    private int status;
    private String message;
    private T[] data;

    public ResultCode(int status,String message,T... data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功
     */
    public ResultCode(T... data) {
        this.status = 200;
        this.message = "success";
        this.data = data;
    }

    public ResultCode(String message ,T... data) {
        this.status = 200;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultCode<T> success(T... data) {
        return new ResultCode<T>(data);
    }

    public static <T> ResultCode<T> success(String message ,T... data) {
        return new ResultCode<T>(message,data);
    }

    public ResultCode(int status,String message){
        this.status = status;
        this.message = message;
    }

    /**
     * 失败
     */
    public static <T> ResultCode<T> failing(String message) {
        int status = 201;
        T data = null;
        return new ResultCode<T>(status,message,data);
    }
    public static <T> ResultCode<T> failing() {
        int status = 201;
        String message = "failing";
        T data = null;
        return new ResultCode<T>(status,message,data);
    }
}