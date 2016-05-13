package com.feximin.library.util.player.audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.mianmian.guild.util.Tool;

import java.io.File;

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

    private AudioPlayer(){
        ensurePlayer();
    }

    private Context mContext;
    public static AudioPlayer obtain(Context context){
        AudioPlayer player = new AudioPlayer();
        player.mContext = context.getApplicationContext();
        return player;
    }

    private void ensurePlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(mp -> startPlay());
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

    public void setOnErrorListener(MediaPlayer.OnErrorListener listener){
        if (mMediaPlayer != null){
            mMediaPlayer.setOnErrorListener((mp, what, extra) -> {
                listener.onError(mp, what, extra);
                mCurStatus = Status.Error;
                mCurPlayingUri = null;
                return true;
            });
        }
    }

    public void setOnCompleteListener(MediaPlayer.OnCompletionListener listener){
        if (mMediaPlayer != null){
            mMediaPlayer.setOnCompletionListener(mp -> {
                listener.onCompletion(mp);
                mCurStatus = Status.Complete;
                mCurPlayingUri = null;
            });
        }
    }

    public void play(int audioResId){
        play(Tool.getResourceUri(audioResId));
    }

    public void play(String filePath){
        play(Uri.fromFile(new File(filePath)));
    }
    public void play(Uri uri){
        ensurePlayer();
        stop();
        mMediaPlayer.reset();
        try {
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
