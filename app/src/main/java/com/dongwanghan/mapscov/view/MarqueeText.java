package com.dongwanghan.mapscov.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**

 * @author: youyu
 * @date: 2021年04月11日 15:08
 */
public class MarqueeText extends androidx.appcompat.widget.AppCompatTextView {
    public MarqueeText(Context context) {
        super(context);
    }

    public MarqueeText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    // 返回始终为true,使该控件获得焦点；
    public boolean isFocused(){
        return true;
    }
}
