package com.example.baseapplication.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.example.baseapplication.TemplateApplication;
/**
 * Toast提示工具
 */
public class ToastMessage {
    private static Toast toast;

    public static void show(String msg) {
        show(TemplateApplication.getInstance(), msg, Toast.LENGTH_SHORT);
    }
    public static void show(String msg, int duration) {
        show(TemplateApplication.getInstance(), msg, duration);
    }

    public static void showLong(String msg) {
        show(TemplateApplication.getInstance(), msg, Toast.LENGTH_LONG);
    }

    public static void show(Context context, int resourceId) {
        show(TemplateApplication.getInstance(), resourceId, Toast.LENGTH_SHORT);
    }

    /**
     * 提示信息
     *
     * @param context  上下文
     * @param msg      要提示的内容
     * @param duration 显示时长
     */
    public static void show(Context context, String msg, int duration) {
        if(msg == null){
            msg = "";
        }
        try {
            toast = Toast.makeText(TemplateApplication.getInstance(),msg, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 提示信息
     *
     * @param context    上下文
     * @param resourceId 资源文件中定义的字符Id
     * @param duration   显示时长
     */
    public static void show(Context context, int resourceId, int duration) {
        try {
            toast = Toast.makeText(TemplateApplication.getInstance(), resourceId, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
