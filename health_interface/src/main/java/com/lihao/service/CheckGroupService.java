package com.lihao.service;

import com.github.pagehelper.PageInfo;
import com.lihao.domain.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    PageInfo getAllCheckGroupByCP(int page, int size, String name);

    void doDel(int id);

    void doAdd(CheckGroup checkGroup, Integer[] checkitemIds);


    CheckGroup getCheckGroupById(int id);

    List<Integer> getCheckitemBygId(int id);

    void doEdit(CheckGroup checkGroup, int[] checkitemIds);
}
