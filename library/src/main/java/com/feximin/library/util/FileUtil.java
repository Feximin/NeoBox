package com.feximin.library.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.mianmian.guild.App;
import com.mianmian.guild.Constant;

import java.io.File;

/**
 * Created by Neo on 15/11/17.
 * 文件存储相关的辅助类
 */
public class FileUtil {

    public static boolean isSDCardExist() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }


    public static void configFilePath() {
        if (Tool.isNotEmpty(Constant.FILE_ROOT)) return;                //	只有在FILE_ROOT为空的时候才进行初始化
        Context app = App.getApp();
        if (FileUtil.isSDCardExist()) {
            File f = app.getExternalCacheDir();
            if (f != null) Constant.FILE_ROOT = f.getAbsolutePath();

            if (TextUtils.isEmpty(Constant.FILE_ROOT)) {
                Constant.FILE_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + app.getPackageName() + "/cache/";
            }
        } else {
//            Constant.FILE_ROOT = app.getCacheDir().getAbsolutePath();
//            if (TextUtils.isEmpty(Constant.FILE_ROOT)) {
                Constant.FILE_ROOT = app.getFilesDir().getParent() + "/cache/";
//            }
        }
        ensureDir(Constant.FILE_ROOT);
        ensureDir(Constant.f_image = Constant.FILE_ROOT + Constant.f_image);
        ensureDir(Constant.f_audio = Constant.FILE_ROOT + Constant.f_audio);
        ensureDir(Constant.f_download = Constant.FILE_ROOT + Constant.f_download);
        ensureDir(Constant.f_apk = Constant.FILE_ROOT + Constant.f_apk);
        ensureDir(Constant.f_video = Constant.FILE_ROOT + Constant.f_video);
    }

    private static void ensureDir(String path){
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    public static void createFolder(String rootPath, String folderName){

    }

    public static long getDirAvalaibleSpace(String path){
        return getDirAvailableSpace(new File(path));
    }

    public static long getDirAvailableSpace(File file){
        StatFs stat = new StatFs(file.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        long size = availableBlocks * blockSize;
        return size;
    }

    public static long getAvailableInternalMemorySize() {
        return getDirAvailableSpace(Environment.getDataDirectory());
    }

    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }


    public static long getAvailableExternalMemorySize() {
        if (isSDCardExist()) {
            return getDirAvailableSpace(Environment.getExternalStorageDirectory());
        } else {
            return -1;
        }
    }

    public static long getTotalExternalMemorySize() {
        if (isSDCardExist()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return -1;
        }
    }
}
