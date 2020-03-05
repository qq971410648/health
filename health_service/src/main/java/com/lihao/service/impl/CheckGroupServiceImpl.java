package com.lihao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lihao.domain.CheckGroup;
import com.lihao.mapper.CheckGroupMapper;
import com.lihao.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @param name
     * @return
     */
    @Override
    public PageInfo getAllCheckGroupByCP(int page, int size, String name) {
        PageHelper.startPage(page, size);
        List<CheckGroup> list = checkGroupMapper.getAllCheckGroupByCP(name);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 根据id删除检查组
     *
     * @param id
     */
    @Override
    public void doDel(int id) {
        //删除中间表
        checkGroupMapper.doDelForMiddle(id);
        //删除检查组新消息
        checkGroupMapper.del(id);
    }

    /**
     * 添加检查组
     *
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void doAdd(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组基本信息
        checkGroupMapper.addCheckGroup(checkGroup);
        //id：插入数据自增得到的id
        Integer id = checkGroup.getId();
        System.out.println(id);

        //添加检查组对应的检查项信息 ————————中间表
        //当新增一个检查组，对应的需要新增其对应的检查项
        // ————————当一个方法需要多有参数的时候   传参方式：3种  采用map
        for (Integer checkitemId : checkitemIds) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("checkgroup_id", id);
            map.put("checkitem_id", checkitemId);
            checkGroupMapper.addForMiddle(map);
        }
    }

    /**
     * 根据id查询检查组信息
     *
     * @param id
     * @return
     */
    @Override
    public CheckGroup getCheckGroupById(int id) {
        return checkGroupMapper.getCheckGroupById(id);
    }

    /**
     * 根据检查组id哈寻对应的检查项id
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> getCheckitemBygId(int id) {
        return checkGroupMapper.getCheckitemBygId(id);
    }

    /**
     * 修改检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void doEdit(CheckGroup checkGroup, int[] checkitemIds) {
        //1. 更新检查组基本信息
        checkGroupMapper.update(checkGroup);

        //2. 根据id删除该检查组对应的所有检查项
        Integer id = checkGroup.getId();
        checkGroupMapper.delItemByGroupId(id);

        //3. 将勾选的检查项id存入
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer itemId : checkitemIds) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("checkgroup_id", id);
                map.put("checkitem_id", itemId);
                checkGroupMapper.addForMiddle(map);
            }
        }
    }
}
