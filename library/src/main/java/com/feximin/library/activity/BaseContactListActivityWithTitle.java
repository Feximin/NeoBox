package com.feximin.library.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.feximin.library.adatper.BaseListAdapter;
import com.mianmian.guild.Constant;
import com.mianmian.guild.R;
import com.mianmian.guild.api.ApiResult;
import com.mianmian.guild.entity.Contact;
import com.mianmian.guild.ui.chat.AdapterContactList;

import java.util.ArrayList;

/**
 * Created by Neo on 16/2/16.
 */
public class BaseContactListActivityWithTitle extends BaseRefreshableListActivityWithTitle<Contact> {

    private Options mCurOptions;

    protected AdapterContactList mAdapterContactList;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        this.clearDivider();
        setLeftButDefaultListener();
        this.mCurOptions = getIntent().getParcelableExtra(Constant.OPTIONS);

    }

    public static void startActivity(Activity activity, Options op){
        Intent intent = new Intent(activity, BaseContactListActivityWithTitle.class);
        intent.putExtra(Constant.OPTIONS, op);
        activity.startActivity(intent);
    }

    @Override
    protected ApiResult doRefresh() {
        return null;
    }

    @Override
    protected ApiResult doLoadMore() {
        return null;
    }

    @Override
    protected BaseListAdapter<Contact> getAdapter() {
        this.mAdapterContactList = new AdapterContactList(mActivity, getViewById(R.id.txt_alpha), mCurOptions);
        return mAdapterContactList;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_contact_list_with_title;
    }

    public static class Options implements Parcelable {
        public int requestCount;            //分为单选和多选
        public ArrayList<Contact> selectedContactList;

        public Options() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.requestCount);
            dest.writeTypedList(selectedContactList);
        }

        protected Options(Parcel in) {
            this.requestCount = in.readInt();
            this.selectedContactList = in.createTypedArrayList(Contact.CREATOR);
        }

        public static final Creator<Options> CREATOR = new Creator<Options>() {
            public Options createFromParcel(Parcel source) {
                return new Options(source);
            }

            public Options[] newArray(int size) {
                return new Options[size];
            }
        };
    }
}
