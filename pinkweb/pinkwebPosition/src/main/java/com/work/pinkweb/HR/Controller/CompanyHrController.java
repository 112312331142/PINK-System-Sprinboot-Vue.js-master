package com.work.pinkweb.HR.Controller;

import com.work.pinkweb.Entity.CompanyHr;
import com.work.pinkweb.HR.Service.CompanyHrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CompanyHrController {

    @Autowired
    private CompanyHrService companyHrService;

    /**
     * 获取HR个人信息
     * @param ehr_id HR的唯一标识
     * @return 返回HR的个人信息对象
     */
    @RequestMapping("/hire/hire_profile/{id}")
    public CompanyHr getCompanyHrInfo(@PathVariable("id") Integer ehr_id) {
        List<CompanyHr> list = companyHrService.getCompanyHrAllInfo(ehr_id);
        CompanyHr a = list.get(0);
        return a;
    }

    /**
     * 更新HR的个人资料
     * @param ehr_id HR的唯一标识
     * @param para 包含要更新的HR信息的参数映射
     * @return 更新操作的结果字符串
     */
    @RequestMapping(value = "/hire/hire_profile/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String updateHrProfile(@PathVariable("id") Integer ehr_id, @RequestBody Map<String, Map> para) {
        // 从参数映射中提取HR的电话、微信和职位信息
        String phone = para.get("params").get("phone").toString();
        String ehr_vx = para.get("params").get("ehr_vx").toString();
        String ehr_position = para.get("params").get("ehr_position").toString();

        // 打印提取的HR信息以供调试
        System.out.println("phone = " + phone);
        System.out.println("ehr_vx = " + ehr_vx);
        System.out.println("ehr_position = " + ehr_position);

        // 调用服务方法更新HR的微信和职位信息
        companyHrService.updateHrInfo(ehr_id, ehr_vx, ehr_position);
        // 调用服务方法更新HR的电话信息
        companyHrService.updateHrPhone(ehr_id, phone);
        return "success";
    }

}
