package com.lihao.controller;

import com.lihao.util.CrowdUtils;
import com.lihao.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 发送手机验证码
     *
     * @param telephone
     * @return
     */
    @RequestMapping("/validateCode/send4Order")
    public ResultEntity send4Order(String telephone) {
        try {
            System.out.println(telephone);
            //0. 生成随机验证码
//            String randomCode = CrowdUtils.randomCode(4);//随机验证码
            String randomCode = "6666";

            //1. 将验证码存入redis
            jedisPool.getResource().setex("tel", 5 * 60, randomCode);

            //2. 发送验证码
            String appCode = "b4b972be537b422194e71d19f525eeef";
            CrowdUtils.sendShortMessage(appCode, randomCode, telephone);

            return ResultEntity.successNoData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
