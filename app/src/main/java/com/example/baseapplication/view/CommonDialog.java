package com.example.baseapplication.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baseapplication.R;

public class CommonDialog {
    Context context;
    Dialog dialog;

    TextView tv_title;
    TextView tv_content;
    private Button btn_cancel, btn_ok;
    private CancelButtonClickListener cancelButtonClickListener;
    private OkButtonClickListener okButtonClickListener;
    private LinearLayout ll_menu;
    private View v_deliver;
    private View v_underline;

    public CommonDialog(Context context) {
        this.context = context;

        try {
            dialog = new Dialog(context, R.style.common_dialog_style);
            Window win = dialog.getWindow();
            if (win != null) {
                win.getDecorView().setPadding(dpToPx(36), 0, dpToPx(36), 0);
                WindowManager.LayoutParams lp = win.getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                win.setAttributes(lp);
            }
            dialog.setContentView(R.layout.dialog_common);
            initView();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private int dpToPx(int dp) {
        return (int) (Resources.getSystem().getDisplayMetrics().density * dp + 0.5f);
    }

    private void initView() {
        tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        tv_content = (TextView) dialog.findViewById(R.id.tv_content);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        ll_menu = (LinearLayout) dialog.findViewById(R.id.ll_menu);
        v_deliver = dialog.findViewById(R.id.v_deliver);
        v_underline = dialog.findViewById(R.id.v_underline);
        tv_title.setVisibility(View.GONE);
        ll_menu.setVisibility(View.GONE);
        v_underline.setVisibility(View.GONE);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cancelButtonClickListener != null){
                    cancelButtonClickListener.onclick();
                }
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(okButtonClickListener != null){
                    okButtonClickListener.onclick();
                }
            }
        });
    }

    public void setMenuAndClick(String cancelText, String okText,
                             CancelButtonClickListener cancelClick, OkButtonClickListener okClick){
        if(!TextUtils.isEmpty(cancelText)){
            ll_menu.setVisibility(View.VISIBLE);
            v_underline.setVisibility(View.VISIBLE);
            btn_cancel.setText(cancelText);
            this.cancelButtonClickListener = cancelClick;
            if(!TextUtils.isEmpty(okText)){
                btn_ok.setVisibility(View.VISIBLE);
                v_deliver.setVisibility(View.VISIBLE);
                btn_ok.setText(okText);
                this.okButtonClickListener = okClick;
            }else {
                btn_ok.setVisibility(View.GONE);
                v_deliver.setVisibility(View.GONE);
            }
        }else {
            btn_cancel.setVisibility(View.GONE);
            v_deliver.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(okText)){
                ll_menu.setVisibility(View.VISIBLE);
                v_underline.setVisibility(View.VISIBLE);
                btn_ok.setVisibility(View.VISIBLE);
                btn_ok.setText(okText);
                this.okButtonClickListener = okClick;
            }else {
                btn_ok.setVisibility(View.GONE);
                v_deliver.setVisibility(View.GONE);
            }
        }
    }

    public void setTitle(String title) {
        if(!TextUtils.isEmpty(title)){
            tv_title.setText(title);
            tv_title.setVisibility(View.VISIBLE);
        }else {
            tv_title.setVisibility(View.GONE);
        }
    }

    public void setContent(String content) { tv_content.setText(content); }

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.hide();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public interface CancelButtonClickListener{ public void onclick(); }

    public interface OkButtonClickListener{ public void onclick(); }
}