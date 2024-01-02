package com.atguigu.wms.common.handler;

import com.atguigu.wms.common.execption.WmsException;
import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.common.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理类
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.build(null, ResultCodeEnum.FAIL.getCode(), e.getMessage());
    }

    /**
     * 自定义异常处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(WmsException.class)
    @ResponseBody
    public Result error(WmsException e){
        return Result.build(null,e.getCode(), e.getMessage());
    }

}
