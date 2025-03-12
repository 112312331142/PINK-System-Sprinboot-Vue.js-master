package com.work.pinkweb.Candidate.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.work.pinkweb.Candidate.Service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 注册控制器类，处理候选人注册过程中的请求
 */
@RequestMapping("/premise/candidate_register")
@RestController
public class RegisterController {
    /**
     * 自动注入注册服务类，用于处理注册相关的业务逻辑
     */
    @Autowired
    private RegisterService registerService;

    /**
     * 处理候选人注册第一步的请求
     * 该方法接收一个包含用户手机号和密码的Map对象，调用注册服务类中的方法进行处理，并返回处理结果
     *
     * @param para 包含用户手机号和密码的Map对象
     * @return 处理结果，通常是一个表示注册状态的整数
     * @throws JsonProcessingException 如果处理JSON数据时发生错误
     */
    @PostMapping("/step_1")
    @ResponseBody
    public Integer candidateRegister(@RequestBody Map<String, Map> para) throws JsonProcessingException {
        // 从请求参数中获取用户的手机号和密码
        String phone = para.get("data").get("phone").toString();
        String password = para.get("data").get("password").toString();
        // 调用服务层方法处理用户注册
        return registerService.candidateRegister(phone, password);
    }

    /**
     * 处理候选人注册第二步的请求
     * 该方法接收一个包含用户手机号、姓名、性别和生日的Map对象，调用注册服务类中的方法完善用户信息，并返回处理结果
     *
     * @param para 包含用户手机号、姓名、性别和生日的Map对象
     * @return 处理结果，通常是一个表示信息更新状态的整数
     * @throws JsonProcessingException 如果处理JSON数据时发生错误
     * @throws ParseException          如果解析日期字符串时发生错误
     */
    @PostMapping("/step_2")
    @ResponseBody
    public Integer updateUserBase(@RequestBody Map<String, Map> para) throws JsonProcessingException, ParseException {
        // 从请求参数中获取用户的手机号、姓名、性别和生日
        String phone = para.get("data").get("phone").toString();
        String name = para.get("data").get("name").toString();
        Integer sex = Integer.valueOf(para.get("data").get("sex").toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthday = para.get("data").get("birthday").toString();
        Date date = simpleDateFormat.parse(birthday);
        // 调用服务层方法完善用户信息
        return registerService.perfectCandidateInfo(phone, name, sex, date);
    }
}
