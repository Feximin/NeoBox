package com.feximin.library.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.mianmian.guild.App;
import com.mianmian.guild.R;
import com.mianmian.guild.interfaces.ILifeCycle;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Neo on 16/5/4.
 */
public class PackageHelper implements BroadcastHelper.OnReceiveListener, ILifeCycle {
    private PackageManager mPackageManager;
    private BroadcastHelper mBroadcastHelper;
    private List<PackageInfo> mInstallPackageList = new ArrayList<>();

    private PackageHelper(){
        mBroadcastHelper = BroadcastHelper.obtain(App.getApp());
        mBroadcastHelper.add(Intent.ACTION_PACKAGE_ADDED, this);
        mBroadcastHelper.add(Intent.ACTION_PACKAGE_REMOVED, this);
        mBroadcastHelper.register();
        refreshInstallPackage();
    }
    public static PackageHelper getInstance(){
        return SingletonFactory.getInstance(PackageHelper.class);
    }

    private synchronized void refreshInstallPackage(){
        if (mPackageManager == null) mPackageManager = App.getApp().getPackageManager();
        List<PackageInfo> infoList = mPackageManager.getInstalledPackages(0);
        if (Tool.isNotEmpty(infoList)){
            mInstallPackageList.clear();
            mInstallPackageList.addAll(infoList);
        }
    }

    public synchronized boolean isInstall(String name){
        for (PackageInfo info : mInstallPackageList){
            if (Tool.isAllNotEmptyAndEquals(name, info.packageName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onReceive(String action, Context context, Intent intent) {
        refreshInstallPackage();
    }

    @Override
    public void onCreate() {

    }

    public void launchApp(Activity activity, String packageName){
        boolean isOk = false;
        try {
            PackageInfo pi = mPackageManager.getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.setPackage(pi.packageName);
            List<ResolveInfo> apps = mPackageManager.queryIntentActivities(resolveIntent, 0);

            ResolveInfo ri;
            if (Tool.isNotEmpty(apps) && (ri = apps.get(0)) != null) {
                packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                activity.startActivity(intent);
                isOk = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (!isOk){
            Tool.showToast(R.string.launch_fail);
        }
    }


    @Override
    public void onDestroy() {
        if (mBroadcastHelper != null) mBroadcastHelper.unRegister();
    }
}
