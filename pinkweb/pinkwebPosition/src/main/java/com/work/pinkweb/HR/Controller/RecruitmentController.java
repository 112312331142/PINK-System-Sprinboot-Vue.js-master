package com.work.pinkweb.HR.Controller;

import com.github.pagehelper.PageInfo;
import com.work.pinkweb.Entity.Recruitment;
import com.work.pinkweb.HR.Service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class RecruitmentController {
    @Autowired
    private RecruitmentService recruitmentService;

    /**
     * 获取所有招聘岗位信息
     *
     * @param pageIndex 当前页码
     * @param pageSize 每页大小
     * @param ehrid 企业人力资源ID
     * @return 包含招聘岗位信息的分页对象
     */
    @RequestMapping("/hire/hire_release/{ehrid}")
    public PageInfo<Recruitment> getAllRecruitments(@RequestParam(value = "pageNow") Integer pageIndex,
                                                    @RequestParam(value = "pageSize") Integer pageSize, @PathVariable Integer ehrid) {
        return recruitmentService.getRecruitmentByEhrid(pageIndex, pageSize, ehrid);
    }

    /**
     * 删除指定ID的招聘信息
     *
     * @param r_id 招聘信息ID
     * @return 删除操作的结果，1表示成功，其他表示失败
     */
    @RequestMapping("/hire/hire_release/delete/{r_id}")
    public Integer deleteRecruitment(@PathVariable("r_id") Integer r_id) {
        return recruitmentService.deleteOneRecruitment(r_id);
    }

    /**
     * 插入新的招聘信息
     *
     * @param recruitment 招聘信息对象，包含招聘信息的各个字段
     * @return 插入操作的结果，1表示成功，其他表示失败
     */
    @RequestMapping("/hire/hire_release/insert")
    public Integer insertRecruitment(@RequestBody Recruitment recruitment) {
        recruitment.setCreated_time(new Date());
        System.err.println(recruitment);
        return recruitmentService.insertOneRecruitment(recruitment);
    }

    /**
     * 根据岗位ID回显岗位信息
     *
     * @param r_id 岗位ID
     * @return 对应岗位ID的招聘信息对象
     */
    @RequestMapping("/hire/release_detail/{r_id}")
    public Recruitment getRecruitmentAppByRid(@PathVariable("r_id") Integer r_id) {
        System.out.println("r_id = " + r_id);
        return recruitmentService.getRecruitmentAppByRid(r_id);
    }

    /**
     * 修改招聘信息
     *
     * @param recruitment 招聘信息对象，包含需要修改的招聘信息及其各个字段
     * @return 修改操作的结果，通常包含修改后的招聘信息或状态码
     */
    @PostMapping("/hire/release_detail/update")
    public Object updateRecruitment(@RequestBody Recruitment recruitment) {
        return recruitmentService.updateRecruitment(recruitment);
    }
}
