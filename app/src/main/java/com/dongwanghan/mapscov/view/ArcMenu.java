package com.dongwanghan.mapscov.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ArcMenu extends ViewGroup implements View.OnClickListener {
    /*
    * @Description: TODO
    * @author: youyu
    * @date:
    *
    
    * @Return:
    */

    /**
     *
     * 菜单主按钮
     */
    private View mCButton;
    /**菜单状态类
     *
     *
     */
    public enum Status{
        OPEN, CLOSE
    }
    /**
     * 菜单的位置枚举类
     *
     */
    enum Position{
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }
    /**
     * 子菜单的回调接口
     *
     */
    public interface OnMenuItemClickListener{
        void onClick(View view, int pos);
    }

    public ArcMenu(Context context) {
        super(context);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
