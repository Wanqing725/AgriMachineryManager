package org.agrimachinerymanager.exception;

import org.agrimachinerymanager.common.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 * 统一处理所有Controller层抛出的异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义BaseException异常
     */
    @ExceptionHandler(BaseException.class)
    public ApiResponse<?> handleBaseException(BaseException e) {
        e.printStackTrace();
        return ApiResponse.fail(400, e.getMessage());
    }

    /**
     * 处理所有未捕获的Exception
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        e.printStackTrace();
        return ApiResponse.fail(500, "服务器内部错误：" + e.getMessage());
    }

    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return ApiResponse.fail(500, "运行时错误：" + e.getMessage());
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ApiResponse<?> handleNullPointerException(NullPointerException e) {
        e.printStackTrace();
        return ApiResponse.fail(500, "空指针异常：操作了空对象");
    }
}