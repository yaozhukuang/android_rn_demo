package com.example.administrator.test.react;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.administrator.test.BuildConfig;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;

/**
 * @author zhanwei
 */
public class RNActivity extends AppCompatActivity
        implements DefaultHardwareBackBtnHandler, PermissionAwareActivity {

    private ReactInstanceManager reactInstanceManager;
    private PermissionListener permissionListener;

    public static void startReactActivity(Context context, String moduleName) {
        startReactActivity(context, moduleName, new Bundle());
    }

    public static void startReactActivity(Context context, String moduleName, Bundle bundle) {
        Intent intent = new Intent(context, RNActivity.class);
        intent.putExtra(RNConstant.MODULE_NAME, moduleName);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reactInstanceManager = RNManagerHelper.getInstance()
                .getReactInstanceManager(getApplication());
        setReactContentView();
    }

    /**
     * 设置contentView
     */
    private void setReactContentView() {
        Bundle bundle = getIntent().getExtras();
        String jsModuleName = getIntent().getStringExtra(RNConstant.MODULE_NAME);
        if (bundle != null) {
            bundle.remove(RNConstant.MODULE_NAME);
        }
        ReactRootView rootView =
                RNViewManager.createReactView(this.getApplication(), jsModuleName, bundle);
        setContentView(rootView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(reactInstanceManager != null) {
            reactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(reactInstanceManager != null) {
            reactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(reactInstanceManager != null) {
            reactInstanceManager.onHostDestroy(this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(reactInstanceManager != null) {
            reactInstanceManager.onActivityResult(this, requestCode, resultCode, data);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if(reactInstanceManager != null) {
            reactInstanceManager.onNewIntent(intent);
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if(reactInstanceManager != null) {
            reactInstanceManager.onBackPressed();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && BuildConfig.DEBUG) {
            reactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissionListener != null && permissionListener.
                onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            permissionListener = null;
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {
        permissionListener = listener;
        requestPermissions(permissions, requestCode);
    }
}
