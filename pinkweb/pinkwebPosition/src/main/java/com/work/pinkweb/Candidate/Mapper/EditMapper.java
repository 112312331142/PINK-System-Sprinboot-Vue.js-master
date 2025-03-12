package com.work.pinkweb.Candidate.Mapper;

import com.work.pinkweb.Entity.Candidate;
import com.work.pinkweb.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 编辑映射器接口，用于处理用户和候选人信息的更新操作
 */
@Mapper
@Repository
public interface EditMapper {

    /**
     * 根据ID获取候选人信息
     *
     * @param id 候选人的ID
     * @return 候选人对象，包含详细信息
     */
    Candidate getCandidateInfo(Integer id);

    /**
     * 更新用户的基本信息
     *
     * @param user 用户对象，包含更新后的信息
     * @return 影响的行数，表示更新操作是否成功
     */
    @Update("update user set name=#{name},phone=#{phone},sex=#{sex},email=#{email},birthday=#{birthday} where id=#{id}")
    Integer modifyUserInfo(User user);

    /**
     * 更新候选人的详细信息，包括教育背景、实习经历等
     *
     * @param candidate 候选人对象，包含更新后的详细信息
     * @return 影响的行数，表示更新操作是否成功
     */
    @Update("update candidate set c_vx=#{c_vx},c_type=#{c_type},c_intro=#{c_intro},c_finternship_enterprise=#{c_finternship_enterprise}," +
            " c_finternship_job=#{c_finternship_job},c_finternship_stime=#{c_finternship_stime}," +
            "c_finternship_etime=#{c_finternship_etime},c_sinternship_enterprise=#{c_sinternship_enterprise} ," +
            "c_sinternship_job=#{c_sinternship_job},c_sinternship_stime=#{c_sinternship_stime},c_sinternship_etime=#{c_sinternship_etime}," +
            "c_fdegree=#{c_fdegree},c_fschool=#{c_fschool},c_fmajor=#{c_fmajor},c_ftime=#{c_ftime}," +
            "c_sdegree=#{c_sdegree},c_sschool=#{c_sschool},c_smajor=#{c_smajor},c_stime=#{c_stime}," +
            "c_exindustry=#{c_exindustry},c_exposition=#{c_exposition},c_excity=#{c_excity},c_exsalary=#{c_exsalary} where c_id=#{c_id}")
    Integer modifyCandidateInfo(Candidate candidate);

    /**
     * 更新用户的头像
     *
     * @param uid 用户ID
     * @param avatar 新的头像URL或路径
     * @return 影响的行数，表示更新操作是否成功
     */
    @Update("update user set avatar=#{avatar} where id=#{uid}")
    Integer avatarUpdate(Integer uid, String avatar);
}
