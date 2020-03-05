package com.lihao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lihao.domain.CheckItem;
import com.lihao.mapper.CheckitemMapper;
import com.lihao.service.CheckitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckitemService.class)
@Transactional
public class CheckitemServiceImpl implements CheckitemService {


    @Autowired
    private CheckitemMapper checkitemMapper;

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @param name
     * @return
     */
    @Override
    public PageInfo getAllCheckitem(int page, int size, String name) {
        String replaceName = name.replaceAll(" ", "");
        PageHelper.startPage(page, size);
        List<CheckItem> list = checkitemMapper.getAllCheckitem(replaceName);
        //PageInfo里面包含了很多信息
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 添加检查项
     *
     * @param checkItem
     */
    @Override
    public void doAdd(CheckItem checkItem) {
        checkitemMapper.doAdd(checkItem);
    }

    /**
     * 根据id删除检查项
     *
     * @param id
     */
    @Override
    public void doDel(int id) {
        checkitemMapper.doDel(id);
    }

    /**
     * 根据id查询检查项
     *
     * @param id
     * @return
     */
    @Override
    public CheckItem getCheckitemById(int id) {
        return checkitemMapper.getCheckitemById(id);
    }

    /**
     * 根据id编辑检查项
     *
     * @param checkItem
     */
    @Override
    public void doEdit(CheckItem checkItem) {
        checkitemMapper.doEdit(checkItem);
    }

    /**
     * 查询所有检查项信息
     *
     * @return
     */
    @Override
    public List<CheckItem> getAllCheckItem() {
        return checkitemMapper.getAllCheckItem();
    }
}
