package com.ly.components.java.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.ly.components.java.utils.Utils;

/**
 * 自定义EditText
 *
 * 实现当上层布局是滚动布局时,输入的字数超过100,按住EditText滚动字体,禁止上层布局滚动
 *
 * 以及当设置了最多字数限制时,toast提示
 */

@SuppressLint("AppCompatCustomView")
public class EditTextWithScrollView extends EditText {

    public EditTextWithScrollView(Context context) {
        this(context, null);
        initTouch(context, null);
    }

    public EditTextWithScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTouch(context,attrs);
    }

    public EditTextWithScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTouch(context, attrs);
    }

    //处理和外层滚动的冲突
    private void initTouch(Context context, AttributeSet attrs) {
        String namespace = "http://schemas.android.com/apk/res/android";
        //获取属性中设置的最大长度
        int maxLength = attrs.getAttributeIntValue(namespace, "maxLength", -1);
        //如果设置了最大长度，给出相应的处理
        if (maxLength > -1) {
            setFilters(new InputFilter[]{new MyLengthFilter(maxLength,context)});
        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 100) {
                    setOnTouchListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                                case MotionEvent.ACTION_UP:
                                    v.getParent().requestDisallowInterceptTouchEvent(false);
                                    break;
                            }
                            return false;
                        }
                    });
                }

                if (hintCountListener!=null){
                    hintCountListener.textChangeCount(s.toString().length());
                }
            }
        });

    }
    class MyLengthFilter implements InputFilter {

        private final int mMax;
        private Context context;

        public MyLengthFilter(int max, Context context) {
            mMax = max;
            this.context = context;
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                   int dstart, int dend) {
            int keep = mMax - (dest.length() - (dend - dstart));
            if (keep <= 0) {
                Utils.toast(context,"最多只能输入"+mMax+"个字");
                return "";
            } else if (keep >= end - start) {
                return null; // keep original
            } else {
                keep += start;
                if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                    --keep;
                    if (keep == start) {
                        return "";
                    }
                }
                return source.subSequence(start, keep);
            }
        }
    }

    private HintCountListener hintCountListener;
    public void setHintCountListener(HintCountListener hintCountListener){
        this.hintCountListener = hintCountListener;
    }
    public interface HintCountListener{
        void textChangeCount(int count);
    }

}
