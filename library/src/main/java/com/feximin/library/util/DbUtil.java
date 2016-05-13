package com.feximin.library.util;

import android.content.Context;

import com.feximin.neodb.core.DBConfig;
import com.feximin.neodb.core.NeoDb;
import com.mianmian.guild.entity.MsgContent;
import com.mianmian.guild.entity.User;

/**
 * Created by Neo on 16/5/11.
 */
public class DbUtil {

    public static void init(Context context){

        DBConfig config = new DBConfig()
                .context(context)
                .name("strawberry_plan")
                .version(1)
                .userIdFetcher(() -> UserUtil.getUserId())
                .addModel(User.class)
                .addModel(MsgContent.class);
        NeoDb.init(config);
    }

}
