package com.feximin.box.api;

import com.feximin.box.util.Tool;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Neo on 15/12/7.
 */
public class ApiResult {

    public int code = -1;
    public String msg;
    public JSONObject data;
    public Object obj;
    public Object obj1;

    public static final int CODE_OK = 0;
    public static final int CODE_ERROR = -10;
    public static final int CODE_TIMEOUT = -11;
    public static final int CODE_INVALID_NETWORK = -12;

    public static final int NO_WORD = 20;


    public static final int CODE_BAD_TOKEN = -90;                //token不正确，但是数据可以返回
    public static final int CODE_INVALID_TOKEN = -99;            //错误的token
    public static final int CODE_NOT_LOGIN = -100;

    public static final int CODE_COIN_NOT_ENOUGH = 111;

    public static final String ERROR = build(CODE_ERROR, "出错了");
    public static final String ERROR_TIMEOUT = build(CODE_TIMEOUT, "服务器连接超时，请检查您的网络");
    public static final String ERROR_INVALID_NETWORK = build(CODE_INVALID_NETWORK, "网络未连接");

    public static final ApiResult OK = new ApiResult(0);

    public ApiResult(){}

    public ApiResult(int code){
        this.code = code;
    }

    public static ApiResult obtain(String msg){
        ApiResult result = new ApiResult();
        result.msg = msg;
        return result;
    }

    public ApiResult(String json){
        JSONObject obj = Tool.getJSONObject(json);
        code = obj.optInt("status_code", -1);               //
        msg = obj.optString("msg", "");
        data = obj.optJSONObject("data");
        Tool.eliminateNull(data);
        if (code == CODE_BAD_TOKEN){
        }else if (code == CODE_INVALID_TOKEN){
        }
    }

    //
    public boolean isOk(){
        return code == 0 || code == CODE_BAD_TOKEN;
    }

    public static String build(int code, String msg){
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", code);
        map.put("msg", msg);
        return new JSONObject(map).toString();
    }

    /**
     * token无效，但是是可以浏览的
     * @return
     */
    public boolean isOkWithTokenInvalid(){
        return code == 0 || code == CODE_BAD_TOKEN;
    }

}
