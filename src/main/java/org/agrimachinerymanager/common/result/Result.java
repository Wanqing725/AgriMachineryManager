package org.agrimachinerymanager.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.agrimachinerymanager.common.constant.MessageConstant;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    /**
     * 编码 200-成功 0-和其它数字为失败
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 成功响应
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 200;
        return result;
    }

    /**
     * 成功响应 携带信息
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<T>();
        result.code = 200;
        result.msg = msg;
        result.data = data;
        return result;
    }

    /**
     * 成功响应 携带数据
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 200;
        result.msg = MessageConstant.OPERATE_SUCCESS;
        return result;
    }

    /**
     * 失败响应
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<T>();
        result.msg = msg;
        result.code = 0;
        return result;
    }
}
