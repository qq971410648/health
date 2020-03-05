package com.lihao.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lihao.domain.CheckGroup;
import com.lihao.domain.Setmeal;
import com.lihao.mapper.SetmealMapper;
import com.lihao.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 分页查询套餐信息
     *
     * @param page
     * @param size
     * @param name
     * @return
     */
    @Override
    public PageInfo getAllSetmeal(int page, int size, String name) {
        PageHelper.startPage(page, size);
        List list = setmealMapper.getAllSetmeal(name);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 删除套餐
     *
     * @param id
     */
    @Override
    public void delSetmeal(int id) {
        //1. 删除中间表
        setmealMapper.delMiddle(id);
        //2. 删除套餐表
        setmealMapper.delSetmealTable(id);
    }

    /**
     * 查询所有检查组信息
     *
     * @return
     */
    @Override
    public List<CheckGroup> getAllCheckGroup() {
        return setmealMapper.getAllCheckGroup();
    }

    /**
     * 添加套餐信息
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void addSetmeal(Setmeal setmeal, int[] checkgroupIds) {
        //1. 添加套餐基本信息
//        setmeal.setImg("111111111111111");
        setmealMapper.addSetmealBase(setmeal);
        //1.1 插入数据后得到它自增的id
        Integer id = setmeal.getId();
        System.out.println(id);

        //2. 添加套餐对应的检查组信息—————————中间表
        //当新建一个套餐信息，对应的需要添加起对应的检查组信息
        // ————————当一个方法需要多有参数的时候   传参方式：3种  采用@Param
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (int checkgroupId : checkgroupIds) {
                setmealMapper.addMiddle(id, checkgroupId);
            }
        }
    }

    /**
     * 根据id查询套餐信息
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal getSetmealById(int id) {
        return setmealMapper.getSetmealById(id);
    }

    /**
     * 根据套餐id查询检查组id集合
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> getGroupIdsById(int id) {
        return setmealMapper.getGroupIdsById(id);
    }

    /**
     * 根据id修改套餐信息
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void editSetmeal(Setmeal setmeal, int[] checkgroupIds) {
        //1. 修改套餐基本信息
        setmealMapper.editSetmealById(setmeal);

        //2. 修改中间表信息
        //2.1 根据套餐id删除中间表相关信息
        Integer id = setmeal.getId();
        setmealMapper.delMiddle(id);
        //2.2 插入中间表相关信息
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (int checkgroupId : checkgroupIds) {
                setmealMapper.addMiddle(id, checkgroupId);
            }
        }
    }

    /**
     * 查询所有套餐信息——————用于前台
     *
     * @return
     */
    @Override
    public List<Setmeal> getAllSetmealForPotal() {
        return setmealMapper.getAllSetmealForPotal();
    }
}
