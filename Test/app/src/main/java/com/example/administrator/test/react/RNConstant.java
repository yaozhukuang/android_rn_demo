package com.example.administrator.test.react;

/**
 * @author zhanwei
 */
public class RNConstant {
    /**
     * react native 中通过AppRegistry.registerComponent注册的模块名
     */
    public static final String MODULE_NAME = "module_name";
    /**
     * react native 存储的根目录ReactFileHelper.getReactRootDir下的assets文件夹
     */
    public static final String RN_DIR = "assets";
    /**
     * 上个版本（相对于当前版本）的react native 存储的根目录
     * RNFileHelper.getReactRootDir下的assets文件夹
     */
    public static final String RN_DIR_OLD = "assets_old";
    /**
     * app assets文件夹中的 react native代码bundle
     */
    public static final String RN_ASSERT = "assets://index.android.bundle";
}
