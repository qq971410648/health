package com.lihao.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.lihao.domain.CheckItem;
import com.lihao.service.CheckitemService;
import com.lihao.util.ResultEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckitemController {

    @Reference
    private CheckitemService checkitemService;

    /**
     * 分页条件查询
     *
     * @param page
     * @param size
     * @param name
     * @return
     */
    @RequestMapping("/checkitem/getAllCheckitem")
    public ResultEntity getAllCheckitem(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String name) {
        try {
            PageInfo pageInfo = checkitemService.getAllCheckitem(page, size, name);
            return ResultEntity.successWithData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 添加检查项
     *
     * @param checkItem
     * @return
     */
    @RequestMapping("/checkitem/doAdd")
    public ResultEntity doAdd(@RequestBody CheckItem checkItem) {
        try {
            checkitemService.doAdd(checkItem);
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 根据id删除检查项
     *
     * @param id
     * @return
     */
    @RequestMapping("/checkitem/doDel")
    public ResultEntity doDel(int id) {
        try {
            checkitemService.doDel(id);
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 根据id查找检查项
     *
     * @param id
     * @return
     */
    @RequestMapping("/checkitem/getCheckitemById")
    public ResultEntity getCheckitemById(int id) {
        try {
            CheckItem checkItem = checkitemService.getCheckitemById(id);
            return ResultEntity.successWithData(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 根据id编辑检查项
     *
     * @param checkItem
     * @return
     */
    @RequestMapping("/checkitem/doEdit")
    public ResultEntity doEdit(@RequestBody CheckItem checkItem) {
        try {
            checkitemService.doEdit(checkItem);
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 获取所有检查项信息
     *
     * @return
     */
    @RequestMapping("/checkitem/getAllCheckItem")
    public ResultEntity getAllCheckItem() {
        try {
            List<CheckItem> list = checkitemService.getAllCheckItem();
            return ResultEntity.successWithData(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
