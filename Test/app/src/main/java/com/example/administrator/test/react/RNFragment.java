package com.example.administrator.test.react;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;

/**
 * @author zhanwei
 */
public class RNFragment extends Fragment implements DefaultHardwareBackBtnHandler {

    private ReactInstanceManager reactInstanceManager;

    public static RNFragment createReactNativeFragment(String name) {
        return createReactNativeFragment(name, null);
    }

    public static RNFragment createReactNativeFragment(String name, Bundle bundle) {
        RNFragment fragment = new RNFragment();
        if(bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(RNConstant.MODULE_NAME, name);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reactInstanceManager = RNManagerHelper.getInstance()
                .getReactInstanceManager(getActivity().getApplication());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = getReactContentView();
        if (rootView == null) {
            rootView = getErrorView();
        }
        return rootView;
    }

    /**
     * 设置contentView
     */
    private View getReactContentView() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            //展示错误页面
            return null;
        }
        String jsModuleName = bundle.getString(RNConstant.MODULE_NAME);
        bundle.remove(RNConstant.MODULE_NAME);
        return RNViewManager.createReactView(getActivity().getApplication(), jsModuleName, bundle);
    }

    /**
     * 设置错误页面，当没有module名时展示
     */
    private View getErrorView() {
        FrameLayout frameLayout = new FrameLayout(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        frameLayout.setLayoutParams(params);
        TextView textView = new TextView(frameLayout.getContext());
        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER;
        textView.setLayoutParams(p);

        String hint = "没有设置module名，无法找到资源";
        textView.setText(hint);

        return frameLayout;
    }

    @Override
    public void onPause() {
        super.onPause();
        reactInstanceManager.onHostPause(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        reactInstanceManager.onHostResume(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reactInstanceManager.onHostDestroy(getActivity());
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        getActivity().onBackPressed();
    }
}
