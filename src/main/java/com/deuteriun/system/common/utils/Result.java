package com.deuteriun.system.common.utils;

import com.deuteriun.system.common.enums.ReturnStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Return to clint with JSON
 * This class define return format is
 * </p>
 *
 * @author Gavinin
 * @since 2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private Object data;


    public Result(ReturnStatus returnStatus) {
        this.code = returnStatus.getStatusCode();
        this.msg = returnStatus.getStatusMessage();
    }

    public static Result success(ReturnStatus status) {
       return new Result(status);

    }

    public static Result success(Object data) {
        Result result = new Result(ReturnStatus.SUCCESS);
        result.setData(data);
        return result;
    }


    public static Result error(ReturnStatus returnStatus, Object data) {
        Result result = new Result(returnStatus);
        result.setData(data);
        return result;
    }

    public static Result error(ReturnStatus returnStatus) {
        return new Result(returnStatus);
    }

}
