package com.ly.components.java.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.ly.components.R;
import com.ly.components.kotlin.utils.StatusBarUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    private static final String REGEX_IDCARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$";

    /**
     * md5 加密
     * @param string 加密的字符串
     * @return
     */
    @NonNull
    public static String md5Engtry(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断是否是合法手机号
     * @param phone 手机号
     * @return
     */
    public static boolean checkPhone(String phone) {

        return Pattern.matches(REGEX_MOBILE, phone);
    }

    /**
     * 判断是否是合法身份证号
     * @param idcard 身份证号
     * @return
     */
    public static boolean checkIdCard(String idcard) {
        return Pattern.matches(REGEX_IDCARD, idcard);
    }

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")){
                    return "";
                }else {
                    return null;
                }

            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }


    //禁止 edittext 输入特殊字符

    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    public static boolean matchSpe(String s){
        String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        return Pattern.matches(speChat,s);
    }

    /**
     * 操作键盘 隐藏则显示,显示则隐藏
     * @param context
     */

    public static void handleKerboard(Context context) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    /**
     * 隐藏键盘
     * @param context
     * @param view
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 设置通知栏背景色为白色,字体黑色
     * @param context
     */
    public static void statusWhite(Activity context) {
        StatusBarUtils.INSTANCE.setColor(context, Color.parseColor("#FFFFFF"));
        StatusBarUtils.INSTANCE.setDarkMode(context);
    }

    /**
     * 设置通知栏背景指定颜色,字体随系统变化
     * @param activity
     * @param colorStr
     */
    public static void statusTheme(Activity activity,String colorStr) {
        StatusBarUtils.INSTANCE.setColor(activity, Color.parseColor(colorStr));
    }

    /**
     * 自定义toast ,背景半透明,居中显示
     * 目前不支持自定义设置背景及位置
     * @param context
     * @param toast
     */
    public static void toast(Context context, String toast) {
        View view = View.inflate(context, R.layout.toast_bg, null);
        TextView textView = view.findViewById(R.id.toast_tv);
        textView.setText(toast);
        Toast toast1 = new Toast(context);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.setDuration(Toast.LENGTH_SHORT);
        toast1.setView(view);
        toast1.show();

    }
    public static void toastLong(Context context, String toast) {
        View view = View.inflate(context, R.layout.toast_bg, null);
        TextView textView = view.findViewById(R.id.toast_tv);
        textView.setText(toast);
        Toast toast1 = new Toast(context);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.setDuration(Toast.LENGTH_LONG);
        toast1.setView(view);
        toast1.show();

    }

    public static void setText(TextView textView,String string){
        if (TextUtils.isEmpty(string)||string.equals("null")){
            string = "- -";
        }

        textView.setText(string);
    }

    public static void setText(TextView textView,Object content){
        if (content == null){
            textView.setText("");
            return;
        }
        if (content instanceof Integer || content instanceof Float || content instanceof Long){
            textView.setText(content+"");
        }else if (content instanceof String){
            textView.setText(content.toString());
        }
    }

    public static void gone(View view){
        view.setVisibility(View.GONE);
    }

    public static void visible(View view){
        view.setVisibility(View.VISIBLE);
    }


    public static void invisible(View view){
        view.setVisibility(View.INVISIBLE);
        view.setEnabled(false);
    }
}
