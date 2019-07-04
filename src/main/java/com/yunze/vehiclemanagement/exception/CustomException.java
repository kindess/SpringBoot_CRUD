package com.yunze.vehiclemanagement.exception;

/**
 * 自定义异常
 */
public class CustomException extends RuntimeException{

    public CustomException(String error){
        super(error);
    }
}
