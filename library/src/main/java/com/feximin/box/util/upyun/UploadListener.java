package com.feximin.box.util.upyun;

/**
 * Created by Neo on 16/5/25.
 */

public interface UploadListener {
    void uploadFinish(boolean status, String remoteUrl);
}
