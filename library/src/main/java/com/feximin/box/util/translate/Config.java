package com.feximin.box.util.translate;

/**
 * Created by Neo on 16/6/17.
 */

public class Config {
    private String appId;
    private String secret;
    private String host = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    private TranslateListener listener;
    private Config(){}
    public static Config obtain(){
        return new Config();
    }

    public Config appId(String id){
        this.appId = id;
        return this;
    }

    public Config secret(String s){
        this.secret = s;
        return this;
    }

    public Config listener(TranslateListener listener){
        this.listener = listener;
        return this;
    }

    public Config host(String h){
        this.host = h;
        return this;
    }
}
