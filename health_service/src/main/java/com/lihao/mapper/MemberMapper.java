package com.lihao.mapper;

import com.lihao.domain.Member;
import org.apache.ibatis.annotations.Select;

public interface MemberMapper {

    //    根据输入的电话号查询会员信息
    @Select("select * from t_member where  phoneNumber = #{telephone}")
    Member findByTelephone(String telephone);

    //    添加会员信息——————需要返回id
    void add(Member member);

    @Select("SELECT COUNT(*) FROM t_member WHERE regTime <= #{s}")
    Integer getMemberByMonth(String s);
}
