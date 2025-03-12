package com.work.pinkweb.HR.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.work.pinkweb.Candidate.Service.LoginService;
import com.work.pinkweb.Entity.User;
import com.work.pinkweb.HR.Service.HrLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * HR登录控制器
 * 处理HR用户的登录和信息获取请求
 */
@RestController
public class HrLoginController {
    /**
     * 自动注入HR登录服务类
     */
    @Autowired
    private HrLoginService hrLoginService;

    /**
     * 处理HR用户登录请求
     * @param para 包含登录信息的Map，包括手机号和密码
     * @return 登录状态字符串，表示登录成功或失败
     * @throws JsonProcessingException 如果处理JSON时发生错误
     */
    @RequestMapping(value = "/premise/hr_login",method = RequestMethod.POST)
    @ResponseBody
    public String applyLogin(@RequestBody Map<String, Map> para) throws JsonProcessingException {
        // 从参数中提取手机号和密码
        String phone=para.get("data").get("phone").toString();
        String password=para.get("data").get("password").toString();
        // 调用服务层方法处理登录逻辑
        String state = hrLoginService.hrLogin(phone,password);
        return state;
    }

    /**
     * 根据手机号获取HR用户信息
     * @param phone HR用户的手机号
     * @return User对象，包含用户信息
     * @throws JsonProcessingException 如果处理JSON时发生错误
     */
    @RequestMapping(value = "/premise/hr_login/{phone}",method = RequestMethod.GET)
    @ResponseBody
    public User getUserInfo(@PathVariable("phone") String phone) throws JsonProcessingException {
        // 调用服务层方法获取HR用户信息
        return hrLoginService.getHrInfo(phone);
    }
}
