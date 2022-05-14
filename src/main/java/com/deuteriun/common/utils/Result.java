package com.deuteriun.common.utils;

import com.deuteriun.common.enums.ReturnStatus;
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
    private String message;
    private String date;
    private String path;
    private Object body;


    public Result(ReturnStatus returnStatus) {
        this.code = returnStatus.getStatusCode();
        this.message = returnStatus.getStatusMessage();
        this.date = DateUtils.currentLocalDataTime();
    }



    public static Result success(ReturnStatus status) {
        return new Result(status);
    }

    public static Result success(ReturnStatus status, Object body) {
        Result result = new Result(status);
        result.setBody(body);
        return result;

    }

    public static Result success(Object data, String path) {
        Result success = success(data);
        success.setPath(path);
        return success;
    }

    public static Result success(Object data) {
        Result result = new Result(ReturnStatus.SUCCESS);
        result.setBody(data);
        return result;
    }


    public static Result error(ReturnStatus returnStatus, Object data) {
        Result result = new Result(returnStatus);
        result.setBody(data);
        return result;
    }

    public static Result error(Object data) {
        Result result = new Result(ReturnStatus.ERROR);
        result.setBody(data);
        return result;
    }

    public static Result error(ReturnStatus returnStatus) {
        return new Result(returnStatus);
    }

}
