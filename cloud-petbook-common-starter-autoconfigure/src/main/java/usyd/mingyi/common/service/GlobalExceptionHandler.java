package usyd.mingyi.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.common.UnauthorizedException;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> unauthorizedExceptionHandler(UnauthorizedException exception) {
        log.info("token异常");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> sqlExceptionHandler(SQLIntegrityConstraintViolationException exception) {
        log.info("存在SQL异常");
        if (exception.getMessage().contains("Duplicate entry")) {
            String[] split = exception.getMessage().split(" ");
            return R.error(split[2] + "已经存在");
        }
        return R.error("未知错误");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        log.info("存在参数异常");

        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        StringBuilder errorMsgs = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errorMsgs.append(fieldName).append(": ").append(errorMessage).append("; ");
        }
        //log.info("参数验证错误信息: " + errorMsgs.toString());

        return R.error(errorMsgs.toString());
    }

/*        @ExceptionHandler(HttpMessageNotReadableException.class)
        public R<String> HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception){
            log.info("日期格式不对");
        return R.error("日期格式不对");
    }*/

    @ExceptionHandler(ConstraintViolationException.class)
    public R<String> constraintViolationExceptionHandler(ConstraintViolationException exception) {
        log.info("参数值有问题");
        return R.error(exception.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public R<String> customExceptionHandler(CustomException exception) {
        log.info("自定义错误");
        return R.error(exception.getMessage());
    }
}
