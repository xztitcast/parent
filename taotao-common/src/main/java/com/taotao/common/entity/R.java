package com.taotao.common.entity;

import com.alibaba.fastjson.JSONObject;
import com.taotao.common.utils.Constant;

public class R extends JSONObject {

	private static final long serialVersionUID = 1L;
	
	public R() {
        put(Constant.CODE, 0);
        put(Constant.MSG, "success");
    }

    public static R error() {
        return error(Constant.ERROR, Constant.EXCE_MSG);
    }

    public static R error(String msg) {
        return error(Constant.ERROR, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put(Constant.CODE, code);
        r.put(Constant.MSG, msg);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R ok(Object value) {
        return ok().put(Constant.RESULT, value);
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
