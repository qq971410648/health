package com.lihao.mapper;

import com.lihao.domain.CheckGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.List;

public interface CheckGroupMapper {

    //    分页查询
    List<CheckGroup> getAllCheckGroupByCP(@Param("name") String name);

    //    删除中间表
    @Delete("delete from t_checkgroup_checkitem where checkgroup_id=#{id}")
    void doDelForMiddle(int id);

    //    删除检查组
    @Delete("delete from t_checkgroup where id = #{id}")
    void del(@Param("id") int id);


    //需要得到添加后的返回值
    void addCheckGroup(CheckGroup checkGroup);

    //添加中间表信息
    void addForMiddle(HashMap<String, Object> map);

    //    根据检查组id查询检查组信息
    @Select("select * from t_checkgroup where id = #{id}")
    CheckGroup getCheckGroupById(int id);

    //    根据检查组id查询对应检查项id集合
    @Select("select checkitem_id from t_checkgroup_checkitem where checkgroup_id = #{id}")
    List<Integer> getCheckitemBygId(int id);

    @Update("update t_checkgroup set code =#{code},name=#{name},helpCode=#{helpCode},sex=#{sex},remark=#{remark},attention=#{attention} where id =#{id}")
    void update(CheckGroup checkGroup);

    @Delete("delete from t_checkgroup_checkitem where checkgroup_id = #{id}")
    void delItemByGroupId(Integer id);
}
