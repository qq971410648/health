package com.lihao.service;

import com.lihao.domain.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    void addOrderSetting(List<OrderSetting> orderSettingList);

    List<Map> getMonthOrderSetting(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
