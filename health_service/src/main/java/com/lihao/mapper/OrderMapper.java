package com.lihao.mapper;

import com.lihao.domain.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

public interface OrderMapper {

    //根据套餐id和用户id查询预约信息
    @Select("select * from t_order where member_id = #{memberId} and setmeal_id=#{setmealId}")
    List<Order> findByCondition(HashMap<String, Object> hashMap);

    //保存预约信息
    @Insert("insert into t_order(member_id,orderDate,orderType,orderStatus,setmeal_id)values(#{memberId},#{orderDate},#{orderType},#{orderStatus},#{setmeal_id})")
    void add(Order order);
}
