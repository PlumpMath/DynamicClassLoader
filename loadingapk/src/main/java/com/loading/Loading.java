package com.loading;

import android.content.Context;

/**
 * Created by zhangming on 16/6/5.
 */
public interface Loading {
    public String getName();

    public boolean startLoading(String msg);

    public boolean startLoading(Context context, String msg);

    String getMessage(Context context, String msg);
}
