package com.work.pinkweb.HR.Mapper;

import com.work.pinkweb.Entity.Apply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ApplyMapper接口用于处理与申请职位相关的数据库操作
 * 它定义了一系列SQL查询，以获取、删除和统计申请信息
 */
@Mapper
public interface ApplyMapper {
    /**
     * 根据岗位ID获取所有申请信息
     *
     * @param r_id 岗位ID，用于筛选申请信息
     * @return 包含所有申请信息的列表
     */
    List<Apply> getAllApply(Integer r_id);

    // 以下两个方法注释被省略，因为它们是示例代码的一部分，并未实现具体功能

    //    Apply uploadApply(Integer uid,Integer r_id);

    //根据apply_id查询，删除
    Apply getCandidateByApply(Integer apply_id);

    /**
     * 根据申请ID删除申请记录
     *
     * @param apply_id 申请ID，用于标识特定的申请记录
     * @return 删除操作影响的行数
     */
    Integer deleteApplyById(Integer apply_id);

    /**
     * 根据时间和岗位ID获取申请信息
     *
     * @param time 日期字符串，用于筛选申请信息
     * @param r_id 岗位ID，用于进一步筛选申请信息
     * @return 符合条件的申请信息列表
     */
    List<Apply> getLineByDate(String time, Integer r_id);

    /**
     * 统计在指定岗位ID和时间范围内提交的申请数量
     *
     * @param i    时间范围的天数，用于计算时间差
     * @param r_id 岗位ID，用于筛选申请记录
     * @return 在指定时间范围内提交的申请数量
     */
    @Select("SELECT count(*) FROM apply WHERE TO_DAYS( NOW( ) ) - TO_DAYS( created_time) < #{i} && TO_DAYS( NOW( ) ) - " +
            "TO_DAYS( created_time) >= (#{i}-1) and p_id = #{r_id}")
    Integer applyChange(Integer i, Integer r_id);

    /**
     * 统计指定人力资源ID和时间范围内提交的申请数量
     * 这个查询涉及多个表的连接，以获取相关申请信息
     *
     * @param i    时间范围的天数，用于计算时间差
     * @param r_id 人力资源ID，用于筛选申请记录
     * @return 在指定时间范围内提交的申请数量
     */
    @Select("SELECT count(*) FROM apply " +
            "left join " +
            "recruitment_info on apply.p_id =recruitment_info.r_id " +
            "left join enterprise_hr on enterprise_hr.ehr_id  = recruitment_info.ehrid " +
            "WHERE TO_DAYS( NOW( ) ) - TO_DAYS( apply.created_time) < #{i} && TO_DAYS( NOW( ) ) - TO_DAYS( apply.created_time) >= (#{i}-1) and enterprise_hr.ehr_id = #{r_id}")
    Integer applyCompanyChange(Integer i, Integer r_id);
}
