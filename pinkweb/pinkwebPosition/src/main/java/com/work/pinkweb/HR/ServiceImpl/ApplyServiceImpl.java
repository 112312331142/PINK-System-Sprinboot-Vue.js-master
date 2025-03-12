package com.work.pinkweb.HR.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.work.pinkweb.Entity.Apply;
import com.work.pinkweb.HR.Mapper.ApplyMapper;
import com.work.pinkweb.HR.Service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 实现申请服务的类
 */
@Service
public class ApplyServiceImpl implements ApplyService {

    /**
     * 注入申请数据访问对象
     */
    @Autowired
    private ApplyMapper applyMapper;

    /**
     * 根据HR的ID获取所有申请信息
     *
     * @param pageIndex 页码
     * @param pageSize  每页大小
     * @param ehr_id    HR的ID
     * @return 包含申请信息的PageInfo对象
     */
    @Override
    public PageInfo<Apply> getAllApply(Integer pageIndex, Integer pageSize, Integer ehr_id) {
        // 启用分页
        PageHelper.startPage(pageIndex, pageSize);
        // 获取所有申请信息
        List<Apply> applylist = applyMapper.getAllApply(ehr_id);
        // 创建并返回包含申请信息的PageInfo对象
        PageInfo<Apply> info = new PageInfo<>(applylist);
        // 遍历申请列表，更新申请人的最高学历信息
        for (int i = 0; i < applylist.size(); i++) {
            Apply apply = applylist.get(i);
            // 根据申请人是否有第二学历，设置最高学历和学校信息
            if (apply.getCandidate().getC_sdegree() == null) {
                apply.getCandidate().setHighDegree(apply.getCandidate().getC_fdegree());
                apply.getCandidate().setHighSchool(apply.getCandidate().getC_fschool());
            } else {
                apply.getCandidate().setHighDegree(apply.getCandidate().getC_sdegree());
                apply.getCandidate().setHighSchool(apply.getCandidate().getC_sschool());
            }
            // 打印申请信息
            System.err.println(apply);
        }
        return info;
    }

    /**
     * 根据申请ID删除申请记录
     *
     * @param apply_id 申请记录的ID
     * @return 删除的记录数
     */
    @Override
    public Integer deleteApplyById(Integer apply_id) {
        // 打印删除成功信息
        System.err.println("删除成功");
        // 调用数据访问对象删除申请记录
        return applyMapper.deleteApplyById(apply_id);
    }

    /**
     * 根据申请ID获取申请人信息
     *
     * @param apply_id 申请记录的ID
     * @return 包含申请人信息的申请对象
     */
    @Override
    public Apply getCandidateByApply(Integer apply_id) {
        // 获取申请记录
        Apply apply = applyMapper.getCandidateByApply(apply_id);
        // 更新申请人的最高学历信息
        if (apply.getCandidate().getC_sdegree() == null) {
            apply.getCandidate().setHighDegree(apply.getCandidate().getC_fdegree());
            apply.getCandidate().setHighSchool(apply.getCandidate().getC_fschool());
        } else {
            apply.getCandidate().setHighDegree(apply.getCandidate().getC_sdegree());
            apply.getCandidate().setHighSchool(apply.getCandidate().getC_sschool());
        }
        return apply;
    }

    /**
     * 根据时间和职位ID获取申请趋势信息
     *
     * @param time 申请时间
     * @param r_id 职位ID
     * @return 包含申请趋势信息的列表
     */
    @Override
    public List<Apply> getLineByDate(String time, Integer r_id) {
        // 调用数据访问对象获取申请趋势信息
        return applyMapper.getLineByDate(time, r_id);
    }

    /**
     * 获取申请状态变化信息
     *
     * @param r_id 职位ID
     * @return 包含申请状态变化信息的列表
     */
    @Override
    public List applyChange(Integer r_id) {
        // 初始化列表存储申请状态变化信息
        List li = new ArrayList();
        // 循环获取不同状态的申请数量
        for (int i = 10; i > 0; i--) {
            li.add(applyMapper.applyChange(i, r_id));
        }
        // 打印申请状态变化信息列表
        System.out.println(li);
        return li;
    }

    /**
     * 获取公司申请状态变化信息
     *
     * @param r_id 职位ID
     * @return 包含公司申请状态变化信息的列表
     */
    @Override
    public List applyCompanyChange(Integer r_id) {
        // 初始化列表存储公司申请状态变化信息
        List li = new ArrayList();
        // 循环获取不同状态的公司申请数量
        for (int i = 10; i > 0; i--) {
            li.add(applyMapper.applyCompanyChange(i, r_id));
        }
        // 打印公司申请状态变化信息列表
        System.out.println(li);
        return li;
    }
}
