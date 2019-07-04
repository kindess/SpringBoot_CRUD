package com.yunze.vehiclemanagement.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理
 *
 * @author pengbinbin
 * @date 2019/6/27
 */
@ControllerAdvice
public class GlobalExceptionHandler {

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
