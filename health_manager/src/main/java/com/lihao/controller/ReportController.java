package com.lihao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lihao.service.MemberService;
import com.lihao.util.ResultEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @RequestMapping("/getMemberReport")
    public ResultEntity getMemberReport(){
        try {
            HashMap<String, Object> hashMap = new HashMap<>();

            //1. 获取前面第12个月的月份
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH,-12);
            System.out.println(calendar.getTime());

            //2. 获取前12个月到今天的月份
            List<String> listMonth = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                calendar.add(Calendar.MONTH,1);
                listMonth.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
            }

            //3. 根据月份查询该月的会员数量
            List<Integer> listCount = memberService.getMemberByMonth(listMonth);

            //4. 封装数据返回前台
            hashMap.put("months",listMonth);
            hashMap.put("memberCount",listCount);
            return ResultEntity.successWithData(hashMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
