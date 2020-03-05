package com.lihao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lihao.domain.Order;
import com.lihao.domain.Setmeal;
import com.lihao.service.OrderService;
import com.lihao.service.SetmealService;
import com.lihao.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
public class OrderController {

    @Reference
    private OrderService orderService;

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 提交预约
     *
     * @param map
     * @return
     */
    @RequestMapping("/order/submit")
    public ResultEntity submit(@RequestBody Map map) {
        try {
            //1. 获取用户输入的手机号
            String telephone = (String) map.get("telephone");

            //2. 从redis中获取缓存的验证码，key为001   失效时间是5分钟
            String randomCode_redis = jedisPool.getResource().get("tel");

            //3. 获取用户输入的验证码
            String validateCode_web = (String) map.get("validateCode");

            //4. 校验手机验证码
            if (randomCode_redis == null || !randomCode_redis.equals(validateCode_web)) {
                return ResultEntity.failed("验证码不正确");
            }

            //5. 校验成功
            //5.1 保存预约人相关信息
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            ResultEntity resultEntity = orderService.order(map);


            //这里我埋下了一个坑，我准备用一个map对象封装当预约成功后的返回信息        我没做完这个功能   PDF文档里面和我做法不一样
            Map data = (Map) resultEntity.getData();
            String orderDate = (String) data.get("orderDate");//体检日期
            String name = (String) data.get("name");//体检人
            String type = (String) map.get("orderType");//体检类型
            String setmealId = (String) map.get("setmealId");
            int memberId = Integer.parseInt(setmealId);
            Setmeal setmealById = setmealService.getSetmealById(memberId);
            String setmealByIdName = setmealById.getName();//套餐名
            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
