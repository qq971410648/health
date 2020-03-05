package com.lihao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lihao.domain.OrderSetting;
import com.lihao.mapper.OrderSettingMapper;
import com.lihao.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;


    /**
     * 上传excel
     *
     * @param
     */
    @Override
    public void addOrderSetting(List<OrderSetting> orderSettingList) {
        for (OrderSetting orderSetting : orderSettingList) {
            Date orderDate = orderSetting.getOrderDate();
            //1. 根据日期判断改日子是否设置了预约
            OrderSetting order = orderSettingMapper.getOrderSettingByDate(orderDate);
            if (order == null) {
                //没有设置就执行添加设置操作
                orderSettingMapper.addOrderSetting(orderSetting);
            } else {
                //执行更新操作
                orderSettingMapper.updateOrderSetting(orderSetting);
            }
        }
    }

    /**
     * 查询当月相关预约信息
     *
     * @param date
     * @return
     */
    @Override
    public List<Map> getMonthOrderSetting(String date) {
        String month_first = date + "-1";//格式2020-1-1
        String month_end = date + "-31";//格式2020-1-31
        List<OrderSetting> list = orderSettingMapper.getMonthOrderSettingByDate(month_first, month_end);

        List<Map> mapList = new ArrayList<Map>();
        for (OrderSetting orderSetting : list) {
            Date orderDate = orderSetting.getOrderDate();//格式2020-X-X
            Integer number = orderSetting.getNumber();

            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("date", orderDate.getDate());//orderDate.getDate()：得到的是当前日子
            hashMap.put("number", number);//可预约人数
            hashMap.put("reservations", orderSetting.getReservations());//已预约人数

            mapList.add(hashMap);
        }
        return mapList;
    }

    /**
     * 修改当天可预约人数
     *
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        OrderSetting orderSettingByDate = orderSettingMapper.getOrderSettingByDate(orderDate);
        if (orderSettingByDate != null) {
            orderSetting.setId(orderSettingByDate.getId());
            orderSettingMapper.updateOrderSetting(orderSetting);
        }
    }
}
