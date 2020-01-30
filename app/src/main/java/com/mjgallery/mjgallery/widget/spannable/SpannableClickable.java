package com.mjgallery.mjgallery.widget.spannable;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;

/**
 * @author yiw
 * @Description:
 * @date 16/1/2 16:32
 */
public abstract class SpannableClickable extends ClickableSpan implements View.OnClickListener {

    private int DEFAULT_COLOR_ID = R.color.color_0AAFFA;
    /**
     * text颜色
     */
    private int textColor ;

    public SpannableClickable() {
        this.textColor = ArmsUtils.getColor(BaseApplication.getInstance(),DEFAULT_COLOR_ID);
    }

    public SpannableClickable(int textColor){
        this.textColor = textColor;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setColor(textColor);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
