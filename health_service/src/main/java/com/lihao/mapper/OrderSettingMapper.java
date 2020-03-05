package com.lihao.mapper;

import com.lihao.domain.OrderSetting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface OrderSettingMapper {

    //    上传excel文件
    @Insert("insert into t_ordersetting (orderDate,number) values(#{orderDate},#{number})")
    void addOrderSetting(OrderSetting orderSetting);

    //    根据日期判断是否设置的预约信息
    @Select("select * from t_ordersetting where orderDate = #{orderDate}")
    OrderSetting getOrderSettingByDate(Date orderDate);

    //    更新预约信息
    @Update("update t_ordersetting set orderDate = #{orderDate},number = #{number} where id = #{id}")
    void updateOrderSetting(OrderSetting orderSetting);

    //    查询当月预约信息
    @Select("select * from t_ordersetting where orderDate between #{month_first} and #{month_end}")
    List<OrderSetting> getMonthOrderSettingByDate(@Param("month_first") String month_first, @Param("month_end") String month_end);
}
