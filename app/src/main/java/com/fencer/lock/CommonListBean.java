package com.fencer.lock;

import java.util.List;

public class CommonListBean<T>{

    private List<T> list ;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
