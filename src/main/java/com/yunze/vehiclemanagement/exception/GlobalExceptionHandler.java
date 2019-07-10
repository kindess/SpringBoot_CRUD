package com.yunze.vehiclemanagement.exception;

import com.yunze.vehiclemanagement.util.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.access.AccessDeniedException;

/**
 * 全局异常处理
 *
 * @author pengbinbin
 * @date 2019/6/27
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 捕获Security框架抛出的权限不足异常
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ResultCode permissionsException(AccessDeniedException exception){
        logger.warn("权限异常，根本原因: "+ exception.getMessage());
        return ResultCode.failing(exception.getMessage());
    }

    /**
     * 运行异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView processException(RuntimeException exception) {
        ModelAndView m = new ModelAndView();
        m.addObject("errorMessage", exception.getMessage());
        m.setViewName("error/500");
        return m;
    }
}
