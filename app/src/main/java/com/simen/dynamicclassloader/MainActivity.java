package com.simen.dynamicclassloader;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loading.Loading;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

/**
 * support jar,apk,aar,dex
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            loadAndroidJar();
        } else if (id == R.id.nav_gallery) {
            loadUninstallApk();
        } else if (id == R.id.nav_slideshow) {
            loadInstallApk("dynamicclassloader.loadingapk");
        } else if (id == R.id.nav_manage) {
            loadDex();
        } else if (id == R.id.res_camera) {
            resAndroidJar();
        } else if (id == R.id.res_gallery) {
            resUninstallApk();
        } else if (id == R.id.res_slideshow) {
            resInstallApk("dynamicclassloader.loadingapk");
        } else if (id == R.id.res_manage) {
            resDex();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * @Title: loadUninstallApk
     * @Description: 动态加载未安装的apk
     */
    private void loadUninstallApk() {
        String path = Environment.getExternalStorageDirectory() + File.separator;
        String filename = "loadingapk-debug.apk";        // 4.1以后不能够将optimizedDirectory设置到sd卡目录， 否则抛出异常.
        File optimizedDirectoryFile = getDir("dex", 0);
        DexClassLoader classLoader = new DexClassLoader(path + filename, optimizedDirectoryFile
                .getAbsolutePath(), null, getClassLoader());
        try {            // 通过反射机制调用， 包名为com.example.loaduninstallapkdemo, 类名为UninstallApkActivity
            Class mLoadClass = classLoader.loadClass("com.loading.MainLoader");
            Constructor constructor = mLoadClass.getConstructor(new Class[]{});
            Object testClass = constructor.newInstance(new Object[]{});
            // 获取getName方法
            Method helloMethod = mLoadClass.getMethod("getName", new Class[]{});
            helloMethod.setAccessible(true);
            Object content = helloMethod.invoke(testClass, new Object[]{});
            Toast.makeText(MainActivity.this, content.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUninstallApk(String msg) {
        String path = Environment.getExternalStorageDirectory() + File.separator;
        String filename = "loadingapk-debug.apk";        // 4.1以后不能够将optimizedDirectory设置到sd卡目录， 否则抛出异常.
        File optimizedDirectoryFile = getDir("dex", 0);
        DexClassLoader classLoader = new DexClassLoader(path + filename, optimizedDirectoryFile
                .getAbsolutePath(), null, getClassLoader());
        try {            // 通过反射机制调用， 包名为com.loading, 类名为MainLoader
            Class mLoadClass = classLoader.loadClass("com.loading.MainLoader");
            Constructor constructor = mLoadClass.getConstructor(new Class[]{});
            Object testClass = constructor.newInstance(new Object[]{});
            // 获取startLoading方法
            Method helloMethod = mLoadClass.getMethod("getMessage", new Class[]{Context.class,
                    String.class});
            helloMethod.setAccessible(true);
            //调用startLoading方法,并注入参数
            Object content = helloMethod.invoke(testClass, new Object[]{this, msg});
            Toast.makeText(MainActivity.this, content.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resInstallApk(String packName) {

    }

    private void resUninstallApk() {

    }

    private void loadInstallApk(String pkgName) {
        try {
            Context context = createPackageContext(pkgName, Context.CONTEXT_IGNORE_SECURITY | Context
                    .CONTEXT_INCLUDE_CODE);
//            // 获取动态加载得到的资源
//            Resources resources = context.getResources();
//            // 过去该apk中的字符串资源"app_name",并且toast出来，apk换肤的实现就是这种原理
//            String toast = resources.getString(resources.getIdentifier("app_name", "string", pkgName));
//            Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            Class cls = context.getClassLoader().loadClass(pkgName + ".MainActivity");
            // 跳转到该Activity
            startActivity(new Intent(this, cls));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: loadandroidJar/loadAndroidAar
     * @Description: 项目工程中必须定义接口， 而被引入的第三方jar包实现这些接口，然后进行动态加载。
     * 相当于第三方按照接口协议来开发， 使得第三方应用可以以插件的形式动态加载到应用平台中。
     */
    private void loadAndroidJar() {
        final File optimizedDexOutputPath = new File(Environment.getExternalStorageDirectory().toString()
                + File.separator + "loadingandroidjar-debug.aar"); //jar also is supported

        BaseDexClassLoader cl = new BaseDexClassLoader(Environment.getExternalStorageDirectory().toString(),
                optimizedDexOutputPath, optimizedDexOutputPath.getAbsolutePath(), getClassLoader());
        Class libProviderClazz = null;
        try {
            // 载入JarLoader类,并且通过反射构建JarLoader对象,然后调用getName方法
            libProviderClazz = cl.loadClass("com.loading.MainLoader");
            Loading loader = (Loading) libProviderClazz.newInstance();
            Toast.makeText(MainActivity.this, loader.getName(), Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void resAndroidJar() {

    }

    private void loadDex() {

    }

    private void resDex() {

    }

}
