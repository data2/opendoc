package com.data2.opendoc.manager.api.dto.output;


public class Resp<T> {
    private int code;
    private String msg;
    private T object;

    public Resp() {
    }

    public Resp(int code, String msg, T object) {
        this.code = code;
        this.msg = msg;
        this.object = object;
    }

    public Resp(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.object = null;
    }

    public Resp ok(T o) {
        return new Resp(0, "success", o);
    }

    public static Resp fail(int code, String failMsg) {
        return new Resp(code, failMsg, null);
    }

    public static Resp fail(String failMsg) {
        return new Resp(1, failMsg, null);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getObject() {
        return object;
    }

}
