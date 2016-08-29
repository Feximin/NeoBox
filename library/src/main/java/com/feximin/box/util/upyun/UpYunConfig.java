package com.feximin.box.util.upyun;

import com.feximin.box.util.Tool;
import com.squareup.okhttp.MediaType;

import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Feximin on 2015/10/20.
 */
public class UpYunConfig {

    private static final String HOST = "b0.upaiyun.com";                     //图片上传后的url
    public static final String UP_HOST = "http://v0.api.upyun.com";        //上传时候的url
    public String bucket;
    public String secret;
    public String suffix;
    public String folder;
    public String remoteUrl;
    public String saveKey;
    public long expiration;
    public String policy;
    public File file;
    public String localPath;
    public MediaType mediaType;
    public String upUrl;
    public String signature;

    public static UpYunConfig obtain(){
        return new UpYunConfig();
    }

    public UpYunConfig folder(String folder){
        this.folder = folder;
        return this;
    }

    public UpYunConfig bucket(String bucket){
        this.bucket = bucket;
        return this;
    }

    public UpYunConfig secret(String secret){
        this.secret = secret;
        return this;
    }

    public UpYunConfig suffix(String suffix){
        this.suffix = suffix;
        return this;
    }
    public UpYunConfig local(String local){
        this.localPath = local;
        return this;
    }

    public UpYunConfig image(){
        this.mediaType = MediaType.parse("image/jpeg");
        return this;
    }

    public UpYunConfig video(){
        this.mediaType = MediaType.parse("image/jpeg");
        return this;
    }

    public UpYunConfig build(){
        String suffix = Tool.extractSuffix(localPath);
        if (Tool.isEmpty(suffix)) suffix = this.suffix;
        Calendar c = Calendar.getInstance();
        StringBuilder sb = new StringBuilder("/")
                .append(folder)
                .append("/")
                .append(c.get(Calendar.YEAR))
                .append("/")
                .append(c.get(Calendar.MONTH)+1)
                .append("/")
                .append(c.get(Calendar.DAY_OF_MONTH))
                .append("/")
                .append(Tool.randomName(suffix));
        saveKey = sb.toString();
        file = new File(localPath);
        expiration = System.currentTimeMillis() / 1000 + 60 * 10;  //10分钟后超时
        remoteUrl = String.format("http://%s.%s%s", bucket, HOST, saveKey);
        upUrl = String.format("%s/%s/", UP_HOST, bucket);
        policy = getPolicy();
        signature = getSignature();
        return this;
    }

    private String getPolicy(){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("bucket", bucket);
        paramMap.put("save-key", saveKey);
        paramMap.put("expiration", expiration);
        JSONObject object = new JSONObject(paramMap);
        String policy = Tool.base64(object.toString());
        return policy;
    }

    private String getSignature(){
        StringBuilder sb = new StringBuilder(policy).append("&").append(secret);
        String signature = Tool.md5(sb.toString());
        return signature;
    }
}
