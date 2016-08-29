package com.feximin.box.util.upyun;

import com.feximin.box.util.Tool;
import com.feximin.box.util.rx.RxHelper;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Neo on 16/5/25.
 */

public class UpYunHelper {

    public static void upload(UpYunConfig params, UploadListener listener){
        RxHelper.emptyIoOb()
                .map(o -> upload(params))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b -> listener.uploadFinish(b, params.remoteUrl), throwable -> listener.uploadFinish(false, null));
    }


    public static void uploadAvatar(String path, UploadListener listener){
        UpYunConfig config = UpYunConfig.obtain()
                .folder("image/avatar")
                .local(path)
                .image()
                .build();
        upload(config, listener);
    }
    public static String uploadAvatar(String path){
        if (Tool.isNotValidFile(path)) return null;
        UpYunConfig config = UpYunConfig.obtain()
                .folder("image/avatar")
                .local(path)
                .image()
                .build();
        if (upload(config)){
            return config.remoteUrl;
        }
        throw  new UploadAvatarError();
    }

    public static boolean upload(UpYunConfig params){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("file", params.file.getName(), RequestBody.create(params.mediaType, params.file))
                .addFormDataPart("policy", params.policy)
                .addFormDataPart("signature", params.signature)
                .build();

        Request request = new Request.Builder()
                .url(params.upUrl)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccessful()){
            return true;
        }
        return false;
    }
}
