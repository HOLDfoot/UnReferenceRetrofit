package com.xxwolo.netlib.net.retrofit;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.xxwolo.netlib.net.retrofit.listener.OnProgressCancelListener;

import java.lang.ref.WeakReference;


public class ProgressDialogHandler extends Handler {

    public static final int SHOW = 1;
    public static final int DISMISS = 2;

    private DialogCommonProgress mDialog;


    private WeakReference<Context> mContext;

    private boolean cancelable;

    private OnProgressCancelListener mListener;
    private String msg;

    public ProgressDialogHandler(WeakReference<Context> context, OnProgressCancelListener listener, boolean cancelable) {
        this.mContext = context;
        this.mListener = listener;
        this.cancelable = cancelable;
    }

    public ProgressDialogHandler(WeakReference<Context> context, String msg, OnProgressCancelListener listener, boolean cancelable) {
        this.mContext = context;
        this.mListener = listener;
        this.msg = msg;
        this.cancelable = cancelable;
    }

    private void initDialog() {
        Context context = mContext.get();
        if (context==null){
            return;
        }

        if (context instanceof Activity){
            if (((Activity)context).isFinishing()){
                return;
            }
        } else {
            return;
        }
        if (mDialog == null) {
            mDialog = new DialogCommonProgress(mContext.get(), msg);
            mDialog.setCancelable(cancelable);
        }
        if (cancelable)
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mListener.onProgressCancel();
                }
            });
        if (!mDialog.isShowing())
            mDialog.show();
    }

    private void dismmisDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SHOW:
                initDialog();
                break;
            case DISMISS:
                dismmisDialog();
                break;
        }
    }
}
