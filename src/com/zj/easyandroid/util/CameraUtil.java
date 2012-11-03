package com.zj.easyandroid.util;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * simple introduction 操作相机的类
 * <p>
 * detailed comment
 * 
 * @author zhoujian 2012-8-31
 * @see
 * @since 1.0
 */
public class CameraUtil {

    private static final String TAG = "CameraUtil";

    private Activity mActivity;



    public CameraUtil(Activity activity) {
        mActivity = activity;
    }



    /**
     * 打开照相机取得全尺寸照片
     * 
     * @param requestCode
     */
    public void openCameraFullSize(int requestCode,String filePath) {
        try {
            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filePath)));
            mActivity.startActivityForResult(it, requestCode);
        } catch (Exception e) {
            Log.e(TAG, "open camera exception", e);
        }
    }



    /**
     * 打开相机取得一张小尺寸照片
     * @param requestCode
     */
    public void openCameraSmallSize(int requestCode) {
        try {
            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mActivity.startActivityForResult(it, requestCode);
        } catch (Exception e) {
            Log.e(TAG, "open camera exception", e);
        }
    }

}
