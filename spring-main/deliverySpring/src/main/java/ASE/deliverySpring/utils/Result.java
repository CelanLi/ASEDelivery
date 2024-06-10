package ASE.deliverySpring.utils;

import java.io.Serializable;

public class Result implements Serializable{

    private static final int ERROR = -1;
    private static final int SUCCESS = 1;
    private static final int OAUTH_ERROR = -2;

    private String message;

    //
    public Object data;

    private int code ;

    private Long count;

    private Integer sizeList;

    public Integer getSizeList() {
        return sizeList;
    }

    public void setSizeList(Integer sizeList) {
        this.sizeList = sizeList;
    }

    public static int getERROR() {
        return ERROR;
    }

    public static int getSUCCESS() {
        return SUCCESS;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Result(String message, Object data, int code, Long count, Integer sizeList){
        this.message = message;
        this.data = data;
        this.code = code;
        this.count = count;
        this.sizeList = sizeList;
    }

    public static Result error(String message){
        return new Result(message,null,ERROR,null,null);
    }

    public static Result success(String message){
        return new Result(message,null,SUCCESS,null,null);
    }

    public static Result success(String message,Object o){
        return new Result(message,o,SUCCESS,null,null);
    }

    public static Result success(String message,Object o,Long count){
        return new Result(message,o,SUCCESS,count,null);
    }

    public static Result success(String message,Object o,Integer sizeList){
        return new Result(message,o,SUCCESS,null,sizeList);
    }
    public static Result oauthError(String message){
        return new Result(message,null,OAUTH_ERROR,null,null);
    }

    @Override
    public String toString() {
        return "Result{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", code=" + code +
                ", count=" + count +
                '}';
    }
}
