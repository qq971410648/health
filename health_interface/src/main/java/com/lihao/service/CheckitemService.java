package com.lihao.service;

import com.github.pagehelper.PageInfo;
import com.lihao.domain.CheckItem;

import java.util.List;

public interface CheckitemService {
    PageInfo getAllCheckitem(int page, int size, String name);

    void doAdd(CheckItem checkItem);

    void doDel(int id);

    CheckItem getCheckitemById(int id);

    void doEdit(CheckItem checkItem);

    List<CheckItem> getAllCheckItem();
}
