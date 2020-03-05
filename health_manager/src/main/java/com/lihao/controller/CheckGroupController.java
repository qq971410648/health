package com.lihao.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.lihao.domain.CheckGroup;
import com.lihao.service.CheckGroupService;
import com.lihao.util.ResultEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 分页条件查询
     *
     * @param page
     * @param size
     * @param name
     * @return
     */
    @RequestMapping("/checkgroup/getAllCheckGroupByCP")
    public ResultEntity getAllCheckGroupByCP(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String name) {
        try {
            PageInfo pageInfo = checkGroupService.getAllCheckGroupByCP(page, size, name);
            return ResultEntity.successWithData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 根据id删除检查组信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/checkgroup/doDel")
    public ResultEntity doDel(int id) {
        try {
            checkGroupService.doDel(id);
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }


    /**
     * 新增检查组信息
     *
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("/checkgroup/doAdd")
    public ResultEntity doAdd(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.doAdd(checkGroup, checkitemIds);
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 根据检查组id查询检查组信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/checkgroup/getCheckGroupById")
    public ResultEntity getCheckGroupById(int id) {
        try {
            CheckGroup checkGroup = checkGroupService.getCheckGroupById(id);
            return ResultEntity.successWithData(checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 根据检查组id查询对应的检查项id
     *
     * @param id
     * @return
     */
    @RequestMapping("/checkgroup/getCheckitemBygId")
    public ResultEntity getCheckitemBygId(int id) {
        try {
            List<Integer> list = checkGroupService.getCheckitemBygId(id);
            return ResultEntity.successWithData(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 编辑检查组
     *
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("/checkgroup/doEdit")
    public ResultEntity doEdit(@RequestBody CheckGroup checkGroup, int[] checkitemIds) {
        try {
            checkGroupService.doEdit(checkGroup, checkitemIds);
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
