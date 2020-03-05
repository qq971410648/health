package com.lihao.mapper;

import com.lihao.domain.CheckGroup;
import com.lihao.domain.Setmeal;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SetmealMapper {
    //    分页查询
    List getAllSetmeal(@Param("name") String name);

    //    根据套餐id删除中间表信息
    @Delete("delete from t_setmeal_checkgroup where setmeal_id =#{id}")
    void delMiddle(int id);

    //    跟据套餐id删除套餐信息
    @Delete("delete from t_setmeal where id = #{id}")
    void delSetmealTable(int id);

    //    查询所有检查组信息
    @Select("select * from t_checkgroup")
    List<CheckGroup> getAllCheckGroup();

    //    添加套餐信息
    void addSetmealBase(Setmeal setmeal);

    //    添加套餐信息————————中间表
    @Insert("insert into t_setmeal_checkgroup (setmeal_id,checkgroup_id) values(#{id},#{checkgroupId})")
    void addMiddle(@Param("id") Integer id, @Param("checkgroupId") int checkgroupId);

    //    根据id查询套餐信息
    @Select("select * from t_setmeal where id = #{id}")
    Setmeal getSetmealById(int id);

    //    根据套餐id查询检查组id集合————————中间表
    @Select("select checkgroup_id from t_setmeal_checkgroup where setmeal_id = #{id}")
    List<Integer> getGroupIdsById(int id);

    //    根据套餐id修改套餐基本信息
    @Update("update t_setmeal set name = #{name},code = #{code},helpCode=#{helpCode},sex=#{sex},age=#{age},price=#{price}, remark=#{remark},attention=#{attention},img=#{img} where id = #{id}")
    void editSetmealById(Setmeal setmeal);

    //    查询所有套餐信息————————用于前台
    @Select("select * from t_setmeal")
    List<Setmeal> getAllSetmealForPotal();

}
