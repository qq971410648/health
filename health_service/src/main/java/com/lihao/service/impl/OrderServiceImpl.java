package com.lihao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lihao.domain.Member;
import com.lihao.domain.Order;
import com.lihao.domain.OrderSetting;
import com.lihao.mapper.MemberMapper;
import com.lihao.mapper.OrderMapper;
import com.lihao.mapper.OrderSettingMapper;
import com.lihao.service.OrderService;
import com.lihao.util.DateUtils;
import com.lihao.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 提交预约
     * <p>
     * 1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
     * 2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
     * <p>
     * <p>
     * 3、检查当前用户是否注册为会员，如果是会员则执行下一步，如果没有注册会员则**自动**完成注册并进行预约
     * 4、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
     *
     * @param map
     * @return
     */
    @Override
    public ResultEntity order(Map map) throws Exception {
        //0. PS：说明一下，这里我建了一个map 用于封装预约成功后的返回信息
        Map<String, Object> objectHashMap = new HashMap<>();

        //1. 检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSettingByDate = orderSettingMapper.getOrderSettingByDate(date);
        if (orderSettingByDate == null) {
            return ResultEntity.failed("该日子还没有相关预约设置信息");
        }

        //2. 检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number = orderSettingByDate.getNumber();//该日子可预约人数
        int reservations = orderSettingByDate.getReservations();//该日子已预约人数
        if (reservations > number) {
            return ResultEntity.failed("对不起该日子已预约满");
        }
        objectHashMap.put("orderDate",orderDate);//封装的体检日期

        //3. 检查当前用户是否注册为会员，如果是会员则执行下一步，如果不是会员则**自动**完成注册并进行预约
        String telephone = (String) map.get("telephone");
        Member byTelephone = memberMapper.findByTelephone(telephone);
        if (byTelephone != null) {//注册过

            //4. 检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
            //4.1 获取用户id
            Integer memberId = byTelephone.getId();
            //4.2 获取用户本次准备预约的这个套餐id
            int setmealId = Integer.parseInt((String) map.get("setmealId"));
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("memberId", memberId);
            hashMap.put("setmealId", setmealId);
            //4.3 查询预约信息表判断用户是否已经预约过此套餐
            List<Order> orderList = orderMapper.findByCondition(hashMap);
            if (orderList != null && orderList.size() > 0) {
                return ResultEntity.failed("对不起，您已经预约过了该套餐了");
            } else {
                //可以预约，设置预约人数加一  
                orderSettingByDate.setReservations(orderSettingByDate.getReservations() + 1);
                //更新当日的已预约人数
                orderSettingMapper.updateOrderSetting(orderSettingByDate);
            }
        }
        if (byTelephone == null) {//没有注册
            //如果没有注册会员则**自动**完成注册并进行预约
            byTelephone = new Member();
            byTelephone.setName((String) map.get("name"));
            byTelephone.setPhoneNumber(telephone);
            byTelephone.setIdCard((String) map.get("idCard"));
            byTelephone.setSex((String) map.get("sex"));
            byTelephone.setRegTime(new Date());
            memberMapper.add(byTelephone);
            System.out.println(byTelephone.getId());
        }
        String name = byTelephone.getName();
        objectHashMap.put("name",name);//封装的体检人

        //将预约信息保存到表中
        Order order = new Order(byTelephone.getId(), date, (String) map.get("orderType"), Order.ORDERSTATUS_NO, Integer.parseInt((String) map.get("setmealId")));
        orderMapper.add(order);

        return ResultEntity.successWithData(objectHashMap);
    }
}
