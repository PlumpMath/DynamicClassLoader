package com.loading;


/**
 * Created by zhangming on 16/6/5.
 */
public class MainLoader implements Loading {
    @Override
    public String getName() {
        return "i am a loadingjar(java)";
    }

    @Override
    public boolean startLoading(String msg) {
        System.out.println(msg);
        return true;
    }

    @Override
    public boolean startLoading(Object context, String msg) {
        System.out.println(msg);
        return true;
    }
}
