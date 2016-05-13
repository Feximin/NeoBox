package com.feximin.library.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neo on 16/1/21.
 */
public class DownloadManager {

    private static final List<Entity> mDownloadEntityList = new ArrayList<>();

    private DownloadManager(){}

    public static DownloadManager getInstance(){
        return SingletonFactory.getInstance(DownloadManager.class);
    }

    public Entity get(String url){
        if(Tool.isEmpty(url)) return null;
        for(Entity entity : mDownloadEntityList){
            if(entity.url.equals(url)){
                return entity;
            }
        }
        return null;
    }



    public static final int NONE = 0;
    public static final int PAUSE = 1;
    public static final int DOWNLOADING = 2;
    @IntDef({NONE, PAUSE, DOWNLOADING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status{}

    public static class Entity implements Parcelable {
        public String url;
        public int status;
        public int progress;
        public int size;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entity entity = (Entity) o;

            return !(url != null ? !url.equals(entity.url) : entity.url != null);

        }

        @Override
        public int hashCode() {
            return url != null ? url.hashCode() : 0;
        }

        public Entity() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.url);
            dest.writeInt(this.status);
            dest.writeInt(this.progress);
            dest.writeInt(this.size);
        }

        protected Entity(Parcel in) {
            this.url = in.readString();
            this.status = in.readInt();
            this.progress = in.readInt();
            this.size = in.readInt();
        }

        public static final Creator<Entity> CREATOR = new Creator<Entity>() {
            public Entity createFromParcel(Parcel source) {
                return new Entity(source);
            }

            public Entity[] newArray(int size) {
                return new Entity[size];
            }
        };
    }
}
