package com.xxwolo.netlib.tool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.view.Window;

import com.xxwolo.netlib.demo.R;

/**
 * Author    ZhuMingren
 * Date      2018/11/30
 * Time      下午3:40
 * DESC      com.xxwolo.netlib.toollib.android.dialog
 */
public abstract class BaseDialog extends Dialog {
    
    public BaseDialog(@NonNull Context context) {
        super(context, R.style.dialogTheme);
        initBaseDialog();
    }
    
    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initBaseDialog();
    }
    
    private void initBaseDialog() {
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setCancelable(getCancelable());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(getCancelable());
    }
    
    protected boolean getCancelable() { return false;}
    
}
