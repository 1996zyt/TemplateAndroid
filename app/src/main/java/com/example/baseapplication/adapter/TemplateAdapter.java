package com.example.baseapplication.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.baseapplication.R;
import com.example.baseapplication.TemplateApplication;
import com.example.baseapplication.response.TemplateBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//万能适配器
public class TemplateAdapter extends BaseQuickAdapter<TemplateBean, BaseViewHolder> {
    public TemplateAdapter(int layoutResId, @Nullable List<TemplateBean> data) {
        super(layoutResId, data);
        //使子项点击事件生效
        addChildClickViewIds(R.id.iv_template);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, TemplateBean templateBean) {
        //获取位置
        int position = baseViewHolder.getLayoutPosition();
        //设置子项
        baseViewHolder.setText(R.id.tv_template,templateBean.getTemplateText());
        //图片加载
        ImageView iv_template = baseViewHolder.getView(R.id.iv_template);
        Glide.with(TemplateApplication.getInstance()).load(templateBean.getTemplateImgUrl()).into(iv_template);
    }
}
