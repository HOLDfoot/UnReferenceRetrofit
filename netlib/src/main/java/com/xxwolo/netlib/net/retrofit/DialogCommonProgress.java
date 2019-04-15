package com.xxwolo.netlib.net.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.xxwolo.netlib.demo.R;
import com.xxwolo.netlib.tool.dialog.BaseDialog;


/**
 * 公用进度
 */
public class DialogCommonProgress extends BaseDialog {


    public DialogCommonProgress(Context context,String strMsg) {
        super(context);
        this.setContentView(R.layout.dialog_common_progress);
        if (this.getWindow() != null) {
            this.getWindow().getAttributes().gravity = Gravity.CENTER;
        }
        TextView tvMsg = (TextView) this.findViewById(R.id.dialog_common_progress_txt);
        if (tvMsg != null && !TextUtils.isEmpty(strMsg)) {
            tvMsg.setText(strMsg);
            tvMsg.setVisibility(View.VISIBLE);
        } else {
            tvMsg.setVisibility(View.GONE);
        }
    }


}

