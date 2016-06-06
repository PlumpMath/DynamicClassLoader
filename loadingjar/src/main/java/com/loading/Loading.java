package com.loading;


/**
 * Created by zhangming on 16/6/5.
 */
public interface Loading {
    public String getName();

    public boolean startLoading(String msg);

    public boolean startLoading(Object context,String msg);
}
