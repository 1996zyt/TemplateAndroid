package com.example.baseapplication.response;

import java.util.List;

public class TemplateResponse extends BaseResponse {
    private List<TemplateBean> list;

    public List<TemplateBean> getList() {
        return list;
    }

    public void setList(List<TemplateBean> list) {
        this.list = list;
    }
}
