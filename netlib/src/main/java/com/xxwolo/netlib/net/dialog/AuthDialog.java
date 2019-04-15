package com.xxwolo.netlib.net.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xxwolo.netlib.demo.R;
import com.xxwolo.netlib.tool.dialog.BaseDialog;

/**
 * Author    ZhuMingren
 * Date      2019/2/22
 * Time      上午11:26
 * DESC      只含有一条消息和"确定"的对话框
 */
public class AuthDialog extends BaseDialog {
    
    private static boolean isShow = false;
    private TextView tvTitle;
    private Button btnConfirm;
    private View ivClose;
    
    private ConfirmCallback mCallback;
    
    public AuthDialog(@NonNull Context context, ConfirmCallback callback) {
        super(context);
        setContentView(getLayoutResId());
        initView();
        tvTitle.setText(context.getString(R.string.account_kick_off));
        this.mCallback = callback;
        ivClose.setVisibility(View.GONE); // 默认Close不可见
    }
    
    public AuthDialog(@NonNull Context context, String msg, boolean withClose, ConfirmCallback ConfirmCallback) {
        this(context, ConfirmCallback);
        ivClose.setVisibility(withClose ? View.VISIBLE : View.GONE);
    }
    
    private void initView() {
        tvTitle = findViewById(R.id.tv_dialog_title);
        ivClose = findViewById(R.id.iv_close);
        btnConfirm = findViewById(R.id.btn_next);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mCallback != null) {
                    mCallback.onConfirm();
                }
            }
        });
    }
    
    @Override
    public void dismiss() {
        super.dismiss();
        isShow = false;
    }
    
    public static boolean isNotShow() {
        return !isShow;
    }
    
    protected int getLayoutResId() {
        return R.layout.dialog_one_msg;
    }
    
    @Override
    protected boolean getCancelable() {
        return false;
    }

    public interface ConfirmCallback {
        void onConfirm();
    }
}
