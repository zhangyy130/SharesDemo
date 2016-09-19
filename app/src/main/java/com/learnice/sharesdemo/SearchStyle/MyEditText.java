package com.learnice.sharesdemo.SearchStyle;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * Created by Slope on 2016/6/20.
 */
public class MyEditText extends EditText {
    private Drawable dRight;
    private Rect rBounds;
    public MyEditText(Context context) {
        super(context);
        initEditText();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEditText();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEditText();
    }
    // 初始化edittext 控件
    private void initEditText(){
        setEditTextDrawable();
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MyEditText.this.setEditTextDrawable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    // 控制图片的显示
    public void setEditTextDrawable(){
        if (getText().toString().length()==0){
            setCompoundDrawables(null, null, null, null);
        }
        else {
            setCompoundDrawables(null, null, this.dRight, null);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.dRight = null;
        this.rBounds = null;
    }
    /**
     * 添加触摸事件 点击之后 出现 清空editText的效果
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if ((this.dRight != null) && (event.getAction() == 1)) {
            this.rBounds = this.dRight.getBounds();
            int i = (int) event.getRawX();// 距离屏幕的距离
            if (i > getRight() - 3 * this.rBounds.width()) {
                setText("");
               event.setAction(MotionEvent.ACTION_CANCEL);
            }
        }
        return super.onTouchEvent(event);
    }
    /**
     * 显示右侧X图片的
     *
     * 左上右下
     */
    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (right != null)
            this.dRight = right;
        super.setCompoundDrawables(left, top, right, bottom);
    }
}
