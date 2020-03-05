package com.lihao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lihao.mapper.MemberMapper;
import com.lihao.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<Integer> getMemberByMonth(List<String> listMonth) {//2020.02
        List<Integer> list = new ArrayList<>();
        for (String s : listMonth) {
            s+="-31";
            System.out.println(s);
            Integer count = memberMapper.getMemberByMonth(s);
            list.add(count);
        }
        return list;
    }
}
