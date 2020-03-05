package com.lihao.mapper;

import com.lihao.domain.CheckItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CheckitemMapper {

    //    分页查询检查项
    List<CheckItem> getAllCheckitem(@Param("name") String name);

    //    添加检查项
    @Insert("insert into t_checkitem (code,name,sex,age,price,type,attention,remark) values(#{code},#{name},#{sex},#{age},#{price},#{type},#{attention},#{remark})")
    void doAdd(CheckItem checkItem);

    //    删除检查项
    @Delete("delete from t_checkitem where id = #{id}")
    void doDel(int id);

    //    根据id查询检查项
    @Select("select * from t_checkitem where id = #{id}")
    CheckItem getCheckitemById(int id);

    //    根据id编辑检查项
    @Update("update t_checkitem set code =#{code},name=#{name},sex=#{sex},age=#{age},price=#{price},type=#{type},remark=#{remark},attention=#{attention} where id =#{id}")
    void doEdit(CheckItem checkItem);

    //    查询所有检查项信息
    @Select("select * from t_checkitem")
    List<CheckItem> getAllCheckItem();
}
