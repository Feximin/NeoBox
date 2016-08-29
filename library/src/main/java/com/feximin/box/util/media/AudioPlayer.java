package com.feximin.box.util.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;

import com.feximin.box.util.SingletonFactory;
import com.feximin.box.util.Tool;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Neo on 16/3/30.
 */
public class AudioPlayer {

    private MediaPlayer mMediaPlayer;
    private Uri mCurPlayingUri;
    private Status mCurStatus = Status.Idle;

    //pending表示发出播放命令到开始播放这个期间的时间
    public static enum Status{
        Idle, Pending, Playing, Pause, Stop, Complete, Error
    }

    private AudioPlayer(){}

    private Context mContext;
    public static AudioPlayer obtain(Context context){
        AudioPlayer player = SingletonFactory.getInstance(AudioPlayer.class);
        if (player.mContext == null || player.mContext != context.getApplicationContext()){
            player.mContext = context.getApplicationContext();
        }
        return player;
    }

    private void ensurePlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();

            mMediaPlayer.setOnCompletionListener(mp -> {
                mCurStatus = Status.Complete;
                mCurPlayingUri = null;
                if (mOnCompletionListener != null){
                    mOnCompletionListener.onCompletion(mMediaPlayer);
                }
            });

            mMediaPlayer.setOnErrorListener((mp, what, extra) -> {
                if (mOnErrorListener != null){
                    mOnErrorListener.onError(mp, what, extra);
                }
                mCurStatus = Status.Error;
                mCurPlayingUri = null;
                return true;
            });
            mMediaPlayer.setOnPreparedListener(mp -> startPlay());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

                try {
                    Class<?> cMediaTimeProvider = Class.forName("android.media.MediaTimeProvider");
                    Class<?> cSubtitleController = Class.forName("android.media.SubtitleController");
                    Class<?> iSubtitleControllerAnchor = Class.forName("android.media.SubtitleController$Anchor");
                    Class<?> iSubtitleControllerListener = Class.forName("android.media.SubtitleController$Listener");

                    Constructor constructor = cSubtitleController.getConstructor(new Class[]{Context.class, cMediaTimeProvider, iSubtitleControllerListener});

                    Object subtitleInstance = constructor.newInstance(mContext, null, null);

                    Field f = cSubtitleController.getDeclaredField("mHandler");

                    f.setAccessible(true);
                    try {
                        f.set(subtitleInstance, new Handler());
                    } catch (IllegalAccessException e) {
                        return;
                    } finally {
                        f.setAccessible(false);
                    }

                    Method setSubtitleAnchor = mMediaPlayer.getClass().getMethod("setSubtitleAnchor", cSubtitleController, iSubtitleControllerAnchor);

                    setSubtitleAnchor.invoke(mMediaPlayer, subtitleInstance, null);
                    //Log.e("", "subtitle is setted :p");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startPlay(){
        //只有当前的状态为pending的时候才去播放
        //有可能已经prepare好的uri并不是当前需要播放的uri
        if (mCurStatus == Status.Pending){
            mMediaPlayer.start();
            mCurStatus = Status.Playing;
        }
    }

    private MediaPlayer.OnErrorListener mOnErrorListener;
    public void setOnErrorListener(MediaPlayer.OnErrorListener listener){
        this.mOnErrorListener = listener;
    }

    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    public void setOnCompleteListener(MediaPlayer.OnCompletionListener listener){
        this.mOnCompletionListener = listener;
    }

    public void play(int audioResId){
        play(Tool.getResourceUri(audioResId));
    }

    public void play(String filePath){
        play(new File(filePath));
    }

    public void play(File file){
        play(Uri.fromFile(file));
    }
    public void play(Uri uri){
        release();
        ensurePlayer();
        mMediaPlayer.reset();
        try {
            mCurStatus = Status.Pending;
            mMediaPlayer.setDataSource(mContext, uri);
            mMediaPlayer.prepareAsync();
            mCurPlayingUri = uri;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 如果当前正在播放的同一个uri，则暂停播放，
     * 如果当前播放的和传入的参数不是同一个，则播放
     * @param uri
     */
    public void togglePlay(Uri uri){
        if (uri == null) return;
        //如果还没有初始化，或者当前没有在播放，则直接播放
        if (mMediaPlayer == null || mCurPlayingUri == null){
            play(uri);
        }else{
            //如果相同，则停止播放
            if (uri.equals(mCurPlayingUri)){
                stop();
            }else{
                //如果不相同则开始播放
                play(uri);
            }
        }
    }

    public void pause(){
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) mMediaPlayer.pause();
        this.mCurStatus = Status.Pause;
    }

    public void stop(){
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) mMediaPlayer.stop();
        mCurStatus = Status.Stop;
    }

    public void release(){
        if (mMediaPlayer != null){
            try {
                if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }catch (IllegalStateException e){
                e.printStackTrace();
            }
        }
        mCurStatus = Status.Idle;
    }
}
