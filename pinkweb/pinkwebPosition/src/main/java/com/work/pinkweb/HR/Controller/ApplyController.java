package com.work.pinkweb.HR.Controller;

import com.github.pagehelper.PageInfo;
import com.work.pinkweb.Entity.Apply;
import com.work.pinkweb.HR.Service.ApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

// 使用@RestController注解表明这是一个Spring MVC的控制器，用于处理HTTP请求
@RestController
public class ApplyController {
    private static final Logger log = LoggerFactory.getLogger(ApplyController.class);
    // 通过@Autowired注解自动注入ApplyService实例
    @Autowired
    private ApplyService applyService;

    // 根据hr的id查询所有申请，参数：hr的id
    @RequestMapping("/hire/hire_attract/{ehrid}")
    public PageInfo<Apply> getByEhr(@RequestParam(value = "pageNow") Integer pageIndex,
                                    @RequestParam(value = "pageSize") Integer pageSize,
                                    @PathVariable("ehrid") Integer ehrid) {
        // 调用服务方法获取分页后的申请信息
        return applyService.getAllApply(pageIndex, pageSize, ehrid);
    }

    // 根据Id删除申请记录
    @RequestMapping("/hire/hire_attract/delete")
    public Integer refuseApply(@RequestParam("apply_id") Integer apply_id) {
        log.info("删除{}", apply_id);
        // 调用服务方法根据申请ID删除申请记录
        return applyService.deleteApplyById(apply_id);
    }

    /**
     * 处理每日申请详情请求
     * 该方法用于获取特定资源在多个日期的申请人数统计
     *
     * @param r_id 资源ID，用于指定需要查询的资源
     * @param dates 日期字符串，包含多个日期，格式为"MM-dd&MM-dd&..."
     * @return 返回一个整数列表，每个元素代表对应日期的申请人数
     */
    @RequestMapping("/hire/release_detail/dailyApply")
    public List<Integer> getDailyApplyLines(@RequestParam("r_id") Integer r_id,
                                            @RequestParam("dates") String dates) {
        // 将传入的日期字符串按'&'分割成日期数组
        String[] sarray = dates.split("&");
        // 初始化一个整数列表，用于存储每个日期的申请人数
        List<Integer> nums = new LinkedList<>();

        // 遍历日期数组，处理每个日期
        for (int i = 0; i < sarray.length; i++) {
            // 将日期字符串转换为"2021-MM-dd"格式
            sarray[i] = "2021-" + sarray[i].substring(2, 7);
            // 打印转换后的日期字符串，用于调试
            System.err.println(sarray[i]);

            // 调用服务方法获取指定日期的申请列表，并打印列表大小，用于调试
            applyService.getLineByDate(sarray[i], r_id);
            System.err.println(applyService.getLineByDate(sarray[i], r_id).size());

            // 再次调用服务方法获取指定日期的申请列表，并将其大小添加到人数统计列表中
            nums.add(applyService.getLineByDate(sarray[i], r_id).size());
        }

        // 返回每个日期的申请人数列表
        return nums;
    }

    // 在查看申请的详情页，根据申请id获取候选人信息
    @RequestMapping("/hire/attract_detail/{apply_id}")
    public Apply getCandiateByApply(@PathVariable("apply_id") Integer apply_id) {
        // 调用服务方法根据申请ID获取候选人信息
        return applyService.getCandidateByApply(apply_id);
    }

    // 处理申请变化
    @RequestMapping("/hire/attract_detail/applyChange/{r_id}")
    public List applyChange(@PathVariable("r_id") Integer r_id) {
        // 调用服务方法处理申请变化
        return applyService.applyChange(r_id);
    }

    // 处理公司申请变化
    @RequestMapping("/hire/attract_detail/applyCompanyChange/{r_id}")
    public List applyCompanyChange(@PathVariable("r_id") Integer r_id) {
        // 调用服务方法处理公司申请变化
        return applyService.applyCompanyChange(r_id);
    }

}
