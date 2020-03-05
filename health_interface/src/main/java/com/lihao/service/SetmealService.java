package com.lihao.service;

import com.github.pagehelper.PageInfo;
import com.lihao.domain.CheckGroup;
import com.lihao.domain.Setmeal;

import java.util.List;

public interface SetmealService {
    PageInfo getAllSetmeal(int page, int size, String name);

    void delSetmeal(int id);

    List<CheckGroup> getAllCheckGroup();

    void addSetmeal(Setmeal setmeal, int[] checkgroupIds);

    Setmeal getSetmealById(int id);

    List<Integer> getGroupIdsById(int id);

    void editSetmeal(Setmeal setmeal, int[] checkgroupIds);

    //查询所有套餐信息  ————用于前台
    List<Setmeal> getAllSetmealForPotal();

}
