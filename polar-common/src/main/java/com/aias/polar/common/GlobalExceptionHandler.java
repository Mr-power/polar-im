package com.aias.polar.common;

import com.aias.polar.common.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @Classname GlobalExceptionHandler
 * @Date 2019/12/15 15:12
 * @Auther liuhy
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 申明捕获哪个异常类
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handle(Exception e) {
        logger.error("request throws exception:", e);
        return ResponseEntity.ok().body(ResultUtils.fail(e.getMessage()));
    }

    /**
     * 参数校验exception统一拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("request throws MethodArgumentNotValidException:{}", e.getMessage());

        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            stringBuilder.append(error.getDefaultMessage());
        }
        return ResponseEntity.ok().body(ResultUtils.fail(stringBuilder.toString()));
    }
}
