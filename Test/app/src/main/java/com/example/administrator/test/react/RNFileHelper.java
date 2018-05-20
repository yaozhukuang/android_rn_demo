package com.example.administrator.test.react;

import android.content.Context;
import android.text.TextUtils;
import com.example.administrator.test.utils.ZipUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhanwei
 */
public class RNFileHelper {

    /**
     * react native 相关存储根目录
     *
     * @param context context
     * @return 路径
     */
    private static String getReactRootDir(Context context) {
//        String path = context.getFileStreamPath("").getPath();
        String path = context.getExternalCacheDir().getPath();
        return path;
    }

    /**
     * 获取react native文件存放路径
     *
     * @param context context
     */
    public static String getReactNativeDir(Context context) {
        File file = new File(getReactRootDir(context), RNConstant.RN_DIR);
        String path = file.getPath();
        return path;
    }

    /**
     * 获取旧版react native文件存放路径，当更新文件出问题的时候可以恢复到旧版
     *
     * @param context context
     */
    private static String getOldReactNativeDir(Context context) {
        File file = new File(getReactRootDir(context), RNConstant.RN_DIR_OLD);
        String path = file.getPath();
        return path;
    }

    /**
     * 恢复旧的react native资源
     *
     * @param context context
     */
    public static boolean recoveryOldReactSource(Context context) {
        RNLog.log("recoveryOldReactSource");
        String recovery = getOldReactNativeDir(context);
        File recoveryFile = new File(recovery);
        if (!recoveryFile.exists()) {
            RNLog.log("recovery file not exist");
            return false;
        }
        String current = getReactNativeDir(context);
        File currentFile = new File(current);
        if (currentFile.exists()) {
            RNLog.log("begin delete file: " + current);
            deleteFile(currentFile);
            RNLog.log("end delete file: " + current);
        }
        return renameFile(recovery, current);
    }

    /**
     * 将assets文件夹中的资源移动到App内
     *
     * @param context context
     */
    public static void moveAssetsResource(Context context) {
        RNLog.log("moveAssetsResource");
        File pre = new File(getReactRootDir(context));
        if (pre.exists()) {
            RNLog.log("begin delete file: " + pre);
            deleteFile(pre);
            RNLog.log("end delete file: " + pre);
        }
        copyAssets(context, "", getReactNativeDir(context));
    }

    /**
     * 将react native更新文件复制到App内
     *
     * @param context context
     * @param srcPath 更新文件路径
     */
    public static void moveUpdateResource(Context context, String srcPath) {
        RNLog.log("moveUpdateResource");
        //更新文件不存在，直接退出
        File src = new File(srcPath);
        if (!src.exists()) {
            RNLog.log("update source not exist");
            return;
        }
        //先将原React Native文件重命名
        String react = RNFileHelper.getReactNativeDir(context);
        File reactFile = new File(react);
        if (reactFile.exists()) {
            String old = getOldReactNativeDir(context);
            //如果已经存在assets_old文件夹，将其删除
            File oldFile = new File(old);
            if (oldFile.exists()) {
                RNLog.log("begin delete file: " + old);
                deleteFile(oldFile);
                RNLog.log("end delete file: " + old);
            }
            //存在assets文件夹的时候将其重命名
            renameFile(react, old);
            //删除assets文件夹
            RNLog.log("begin delete file: " + reactFile);
            deleteFile(reactFile);
            RNLog.log("end delete file: " + reactFile);
        }
        //将更新文件解压出来
        try {
            String parent = new File(getReactNativeDir(context)).getParent();
            //解压,压缩包里面不能有中文，如果要使用中文需要使用第三方的zip代码不是用系统代码
            ZipUtils.unzipFile(srcPath, parent);
            File result = new File(parent, src.getName());
            //重命名
            if (result.exists()) {
                renameFile(result.getPath(), parent + File.separator + RNConstant.RN_DIR);
            }
            //删除更新包
            RNLog.log("begin delete file: " + src);
//            deleteFile(src);
            RNLog.log("end delete file: " + src);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归调用将assets文件夹下的所有文件复制到app目录中
     *
     * @param context context
     * @param srcPath 目标文件
     * @param dstPath 复制地址
     */
    private static void copyAssets(Context context, String srcPath, String dstPath) {
        try {
            String[] files = context.getAssets().list(srcPath);
            if (files != null && files.length > 0) {
                new File(dstPath).mkdir();
                for (String fileName : files) {
                    if (TextUtils.isEmpty(srcPath)) {
                        copyAssets(context, fileName, dstPath + File.separator + fileName);
                    } else {
                        copyAssets(context, srcPath + File.separator + fileName, dstPath + File.separator + fileName);
                    }
                }
            } else {
                copyFile(context.getAssets().open(srcPath), dstPath);
                RNLog.log("copyAssets " + srcPath + " to " + dstPath + ": succeed");
            }
        } catch (IOException e) {
            e.printStackTrace();
            RNLog.log("copyAssets " + srcPath + " to " + dstPath + ": failed" );
        }
    }

    /**
     * 将InputStream流写入文件
     *
     * @param is      输入流
     * @param dstPath 文件地址
     * @throws IOException IO异常
     */
    private static void copyFile(InputStream is, String dstPath) throws IOException {
        File dstFile = new File(dstPath);
        if (dstFile.isDirectory()) {
            return;
        }
        FileOutputStream fos = new FileOutputStream(dstFile);
        byte[] buffer = new byte[1024];
        int byteCount;
        while ((byteCount = is.read(buffer)) != -1) {
            fos.write(buffer, 0, byteCount);
        }
        fos.flush();
        is.close();
        fos.close();
    }

    /**
     * 重命名文件
     *
     * @param oldPath 原来的文件地址
     * @param newPath 新的文件地址
     */
    private static boolean renameFile(String oldPath, String newPath) {
        File oleFile = new File(oldPath);
        File newFile = new File(newPath);
        boolean result = oleFile.renameTo(newFile);
        RNLog.log("rename "+ oldPath + " to " + newPath + ": " + result);
        return result;
    }

    /**
     * 删除文件（夹）
     *
     * @param file 待删除文件（夹
     */
    private static void deleteFile(File file) {
        if (!file.exists()) {
            RNLog.log("delete file" + file.getPath() + ": not exist" );
            return;
        }
        String[] children = file.list();
        if (children == null || children.length == 0) {
            boolean result = file.delete();
            RNLog.log("delete file" + file.getPath() + ": " + result);
        } else {
            for (String child : children) {
                //删除文件夹里面的文件
                deleteFile(new File(file.getPath() + File.separator + child));
                //删除空文件夹
                deleteFile(new File(file.getPath()));
            }
        }
    }
}
