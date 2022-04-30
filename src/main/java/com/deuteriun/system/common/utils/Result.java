package com.deuteriun.system.common.utils;

import com.deuteriun.system.common.enums.ReturnStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
        this.date = ConstUtils.DATE_TIME_FORMAT.format(new Date());
    }

    public static Result success(ReturnStatus status) {
        return new Result(status);

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

    public static Result error(ReturnStatus returnStatus) {
        return new Result(returnStatus);
    }

}
