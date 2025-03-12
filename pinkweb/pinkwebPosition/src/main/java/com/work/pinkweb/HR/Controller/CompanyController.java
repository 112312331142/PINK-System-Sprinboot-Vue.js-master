package com.work.pinkweb.HR.Controller;

import com.work.pinkweb.Entity.Company;
import com.work.pinkweb.HR.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 公司信息控制器
 * 用于处理与公司信息相关的HTTP请求
 */
@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 根据企业ID获取公司信息
     * 此方法会根据提供的企业ID查询并返回公司的详细信息，包括公司的人力资源数量和职位数量
     *
     * @param ea_id 企业ID
     * @return Company对象，包含公司基本信息、人力资源数量和职位数量
     */
    @RequestMapping("/hire/hire_profile/company/{ea_id}")
    public Company getCompanyInfo(@PathVariable("ea_id") Integer ea_id){
        // 打印接收的企业ID以供调试
        System.out.println("ea_id = "+ea_id);

        // 通过公司服务按ID获取公司信息
        List<Company> list1=companyService.getRCompanyById(ea_id);
        // 通过公司服务按ID获取该公司的人力资源信息
        List<Company> list2= companyService.getHrCompanyById(ea_id);

        // 获取第一个查询结果作为基础公司信息
        Company a=list1.get(0);
        // 设置公司的人力资源数量
        a.setHrNum(list1.get(0).getCompanyHrList().size());
        // 设置公司的职位数量
        a.setPositionNum(list1.get(0).getRecruitmentList().size());

        // 返回整合后的公司信息
        return a;
    }


}
