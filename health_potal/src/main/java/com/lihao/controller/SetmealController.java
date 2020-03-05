package com.lihao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lihao.domain.Setmeal;
import com.lihao.service.SetmealService;
import com.lihao.util.ResultEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SetmealController {

    @Reference
    private SetmealService setmealService;


    /**
     * 查询体检套餐信息
     *
     * @return
     */
    @RequestMapping("/setmeal/getAllSetmeal")
    public ResultEntity getAllSetmeal() {
        try {
            List<Setmeal> list = setmealService.getAllSetmealForPotal();
            return ResultEntity.successWithData(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 查询体检预约详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/setmeal/findById")
    public ResultEntity findById(int id) {
//        try {
//            Setmeal setmeal = setmealService.findById(id);
//            return ResultEntity.successWithData(setmeal);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultEntity.failed(e.getMessage());
//        }
        return ResultEntity.failed("123");
    }

    /**
     * 发起预约         根据id查询套餐信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/setmeal/getSetmealById")
    public ResultEntity getSetmealById(int id) {
        try {
            Setmeal setmeal = setmealService.getSetmealById(id);
            return ResultEntity.successWithData(setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
