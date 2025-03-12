package com.work.pinkweb.Candidate.Controller;

import com.github.pagehelper.PageInfo;
import com.work.pinkweb.Entity.Company;
import com.work.pinkweb.Entity.CompanyHr;
import com.work.pinkweb.Candidate.Service.CaCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CaCompanyController {
    @Autowired
    private CaCompanyService caCompanyService;

    @RequestMapping("/index/apply_home/hotCompany")
    public List<Company> getHotCompany() {
        return caCompanyService.getHotCompany();
    }

    @RequestMapping(value = "/index/company_detail/{e_id}", method = RequestMethod.GET)
    public Company getCompanyDetailById(@PathVariable Integer e_id) {
        return caCompanyService.getCompanyDetailbById(e_id);
    }

    // 用于根据公司ID获取公司的人力资源信息
    @RequestMapping(value = "/index/company_detail/joblist/{e_id}",
            method = RequestMethod.GET)
    public Company getCompanyHr(@PathVariable Integer e_id) {
        return caCompanyService.getCompanyHr(e_id);
    }

    // 用于根据公司ID获取公司的招聘信息
    @RequestMapping(value = "/index/company_detail/hrlist/{e_id}",
            method = RequestMethod.GET)
    public List<CompanyHr> getHrRecruitment(@PathVariable Integer e_id) {
        return caCompanyService.getHrRecruitment(e_id);
    }

    // 用于根据条件查询公司信息并分页返回
    @RequestMapping("/index/apply_company/query")
    @ResponseBody
    public PageInfo<Company> getCompanyByCondition(
            @RequestBody Map<String, Map> para,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "flag", required = false) String flag) {
        Integer pageIndex = Integer.valueOf(para.get("params").get("pageNow").toString());
        Integer pageSize = Integer.valueOf(para.get("params").get("pageSize").toString());
        return caCompanyService.getCompanyByCondition(pageIndex, pageSize, key, flag);
    }


}
