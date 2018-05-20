package com.example.administrator.test.react;

import android.app.Application;
import android.os.Bundle;

import com.facebook.react.ReactRootView;

/**
 * @author zhanwei
 */
public class RNViewManager {

    public static ReactRootView createReactView(Application context, String name, Bundle bundle) {
        ReactRootView rootView = new ReactRootView(context);
        rootView.startReactApplication(
                RNManagerHelper.getInstance().getReactInstanceManager(context), name, bundle);
        return rootView;
    }

    public static ReactRootView createReactView(Application context, String name) {
        return createReactView(context, name, null);
    }
}
