package org.agrimachinerymanager.exception;

import lombok.extern.slf4j.Slf4j;
import org.agrimachinerymanager.common.result.ApiResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 * 统一处理所有Controller层抛出的异常
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义BaseException异常
     */
    @ExceptionHandler(BaseException.class)
    public ApiResponse<?> handleBaseException(BaseException e) {
        log.warn("业务异常: {}", e.getMessage());
        return ApiResponse.fail(400, e.getMessage());
    }

    /**
     * 处理Spring Security用户名未找到异常
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ApiResponse<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.warn("用户认证失败: {}", e.getMessage());
        return ApiResponse.fail(401, "用户不存在或用户名错误");
    }

    /**
     * 处理Spring Security权限不足异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<?> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("权限不足: {}", e.getMessage());
        return ApiResponse.fail(403, "权限不足，无法访问该资源");
    }

    /**
     * 处理参数校验异常（@Valid）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "参数校验失败";
        log.warn("参数校验失败: {}", errorMessage);
        return ApiResponse.fail(400, errorMessage);
    }

    /**
     * 处理参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public ApiResponse<?> handleBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "参数绑定失败";
        log.warn("参数绑定失败: {}", errorMessage);
        return ApiResponse.fail(400, errorMessage);
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ApiResponse<?> handleNullPointerException(NullPointerException e) {
        log.error("空指针异常", e);
        return ApiResponse.fail(500, "系统内部错误：操作了空对象");
    }

    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常: {}", e.getMessage(), e);
        return ApiResponse.fail(500, "系统运行错误");
    }

    /**
     * 处理所有未捕获的Exception
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        log.error("未知异常: {}", e.getMessage(), e);
        return ApiResponse.fail(500, "服务器内部错误");
    }
}