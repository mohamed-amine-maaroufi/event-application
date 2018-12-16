package com.tn.blasti.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by amine 15/12/2018.
 */
public class MontserratTextView extends android.support.v7.widget.AppCompatTextView {

    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public MontserratTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MontserratTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public MontserratTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        init(defStyle);
    }

    private void init() {
        Typeface regularFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Regular.ttf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Bold.ttf");

        Typeface currentTypeFace = this.getTypeface();
        if (currentTypeFace != null && currentTypeFace.getStyle() == Typeface.BOLD) {
            this.setTypeface(boldFont);
        } else {
            this.setTypeface(regularFont);
        }

    }

    private void init(int style) {
        Typeface regularFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Regular.ttf");
        Typeface boldFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Montserrat-Bold.ttf");

        Typeface currentTypeFace = this.getTypeface();
        if (currentTypeFace != null && currentTypeFace.getStyle() == Typeface.BOLD) {
            this.setTypeface(boldFont, style);
        } else {
            this.setTypeface(regularFont, style);
        }
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);
    }

}
