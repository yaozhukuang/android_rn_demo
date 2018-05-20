package com.example.administrator.test.react;

import android.app.Application;
import android.util.Log;

import com.example.administrator.test.BuildConfig;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

import java.io.File;

/**
 * @author zhanwei
 */
public class RNManagerHelper {

    private static RNManagerHelper instance;
    private ReactInstanceManager manager;

    private RNManagerHelper() {
    }

    public static RNManagerHelper getInstance() {
        if (instance == null) {
            synchronized (RNManagerHelper.class) {
                if (instance == null) {
                    instance = new RNManagerHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 创建ReactInstanceManager 用于管理js文件的类
     */
    public ReactInstanceManager getReactInstanceManager(Application application) {
        if (manager == null) {
            ReactInstanceManagerBuilder builder = ReactInstanceManager.builder()
                    .setApplication(application)
                    .addPackage(new MainReactPackage())
                    .setUseDeveloperSupport(getUseDeveloperSupport())
                    .setInitialLifecycleState(LifecycleState.BEFORE_RESUME);
//            if(BuildConfig.DEBUG) {
//                builder.setJSMainModulePath("index.android");
//            }
            builder.setJSBundleFile(getJSBundleFile(application));
            manager = builder.build();
        }
        return manager;
    }

    /**
     * 重新加载 ReactInstanceManager
     *
     * @param application 上下文
     * @return ReactInstanceManager
     */
    public ReactInstanceManager reload(Application application) {
        if (manager != null) {
            manager.destroy();
            manager = null;
        }
        return getReactInstanceManager(application);
    }

    /**
     * 是否是开发者模式
     *
     * @return 支持true , 不支持 false
     */
    private boolean getUseDeveloperSupport() {
        return BuildConfig.DEBUG;
    }

    /**
     * 获取bundle file,这样的话每次安装之后都要先把bundle先移动到react目录中，因为旧版热更新过之后react
     * 目录中必然会存在一份bundle文件，如果此时app升级后assets文件夹中的bundle才是最新的，但是本函数优先
     * 取react目录中的文件取不到才会去获取assets文件夹中的bundle
     *
     * @return 文件路径
     */
    private String getJSBundleFile(Application application) {
        String pPath = RNFileHelper.getReactNativeDir(application.getApplicationContext());
        File patchBundleFile = new File(pPath, "index.android.bundle");
        if (patchBundleFile.exists()) {
            RNLog.log("load from data: " + patchBundleFile.getPath());
            return patchBundleFile.getPath();
        }
        RNLog.log("load from assets: " + RNConstant.RN_ASSERT);
        return RNConstant.RN_ASSERT;
    }
}
