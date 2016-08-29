package com.feximin.box.api;

import android.text.TextUtils;

import com.feximin.box.Box;
import com.feximin.box.Constant;
import com.feximin.box.util.NetworkUtil;
import com.feximin.box.util.Tool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by Neo on 15/11/17.
 */
public abstract class AbsApi {

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    static{
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);
    }
    public String callApi(Map<String, Object> mParams){
        String error =  ApiResult.ERROR;
        if(NetworkUtil.isNetworkConnected()){
            try {
                FormEncodingBuilder formBodyBuilder = new FormEncodingBuilder();
                String methodName = (String) mParams.get(Constant.METHOD);
                methodName = TextUtils.isEmpty(methodName) ? "" : methodName;
                String url = Constant.SERVER_URL + methodName;
//                mParams.remove(Constant.METHOD);
                String data = new JSONObject(mParams).toString();
                for (Map.Entry<String, Object> entry : mParams.entrySet()){
                    formBodyBuilder.add(entry.getKey(), entry.getValue().toString());
                }
//                if (Config.sEncrypt) data = EncryptUtil.encrypt(data, Constant.API_KEY);
//                formBodyBuilder.add(Constant.API_VERSION, apiVersion);
//                formBodyBuilder.add(Constant.DATA, data);
                RequestBody formBody = formBodyBuilder.build();
//                RequestBody formBody = RequestBody.create(JSON, data);
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                Response response = mOkHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    return result;
                }

            }catch (SocketTimeoutException e) {
                e.printStackTrace();
                error =  ApiResult.ERROR_TIMEOUT;
            } catch (Exception e) {
                e.printStackTrace();
                error =  ApiResult.ERROR_TIMEOUT;
            }
        }else{
            error = ApiResult.ERROR_INVALID_NETWORK;
        }
        return error;
    }

    public String callGet(String url){
        String error =  ApiResult.ERROR;
        if(NetworkUtil.isNetworkConnected()){
            try {
                return callSimpleGet(url, error);
            }catch (SocketTimeoutException e) {
                e.printStackTrace();
                error =  ApiResult.ERROR_TIMEOUT;
            } catch (Exception e) {
                e.printStackTrace();
                error =  ApiResult.ERROR_TIMEOUT;
            }
        }else{
            error = ApiResult.ERROR_INVALID_NETWORK;
        }
        return error;
    }

    public String callSimpleGet(String url, String... defaultV) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            return result;
        }
        return Tool.getFirstObject(defaultV);

    }

    protected ApiResult doCallApi(Map<String, Object> params){
        String result = callApi(params);

        if (Box.sTestMode) {
//        result = "{\"code\":0, \"msg\":\"ok\", \"data\":{\"image\":[12, \"xxx.jpg\", null]}}";
        }
        return new ApiResult(result);
    }

    protected ApiResult doCallApi(Map<String, Object> params, int atLeast){
        long start = System.currentTimeMillis();
        ApiResult result = doCallApi(params);
        Tool.sleepUntil(atLeast, start);
        return result;
    }


    protected Map<String, Object> getApiParams(String methodName){
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.DEVICE_ID, Tool.getImei());
        map.put(Constant.USER_SYS, 1);
        map.put(Constant.SYS_VERSION_ID, Tool.getCurrentVersionCode());
        map.put(Constant.METHOD, methodName);
        map.put(Constant.REQUEST_NUM, 10);
        return map;
    }

    private final Gson GSON_INSTANCE = new Gson();
    protected Map<String, Object> getApiParams(String methodName, String lastId){
        Map<String, Object> map = getApiParams(methodName);
        map.put(Constant.LAST_ID, lastId);
        return map;
    }

    protected <T> List<T> getListFromApiResult(ApiResult result, String fieldName, Class<T> clazz){
        return getListFromJson(result.data.optString(fieldName), clazz);
    }

    protected <T> List<T> getListFromJson(String jsonStr, Class<T> clazz){
        List<T> list = GSON_INSTANCE.fromJson(jsonStr, new TypeToken<List<T>>(){}.getType());
        return list;
    }

    protected <T> T getObjectFromApiResult(ApiResult result, String fieldName, Class<T> clazz){
        return getObjectFromJson(result.data.optString(fieldName), clazz);
    }

    protected <T> T getObjectFromJson(String jsonStr, Class<T> clazz){
        T t = GSON_INSTANCE.fromJson(jsonStr, clazz);
        return t;
    }


    protected boolean isRefresh(String lastId){
        return Tool.isEmpty(lastId) || lastId.equals("0");
    }
}
