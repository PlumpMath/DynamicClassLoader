package com.loading;


import android.content.Context;

import dynamicclassloader.loadingapk.R;

/**
 * Created by zhangming on 16/6/5.
 */
public class MainLoader implements Loading {
    @Override
    public String getName() {
        return "i am a loadingapk";
    }

    @Override
    public boolean startLoading(String msg) {
        System.out.println(msg);
        return true;
    }

    @Override
    public boolean startLoading(Context context, String msg) {
        System.out.println((msg == null ? "" : msg) + ":" + context.getString(R.string.app_name));
        return true;
    }

    @Override
    public String getMessage(Context context, String msg) {
        return (msg == null ? "" : msg) + ":" + context.getString(R.string.app_name);
    }
}
