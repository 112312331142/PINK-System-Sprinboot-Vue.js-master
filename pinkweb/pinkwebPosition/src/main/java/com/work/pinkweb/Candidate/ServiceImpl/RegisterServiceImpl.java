package com.work.pinkweb.Candidate.ServiceImpl;

import com.work.pinkweb.Candidate.Mapper.RegisterMapper;
import com.work.pinkweb.Candidate.Service.RegisterService;
import com.work.pinkweb.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 实现用户注册服务的类
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private RegisterMapper registerMapper;

    /**
     * 处理候选人的注册流程
     *
     * @param phone    用户的电话号码，作为唯一标识
     * @param password 用户设置的密码
     * @return 返回候选人的注册结果
     */
    @Override
    public Integer candidateRegister(String phone, String password) {
        //注册user身份
        registerMapper.userRegister(phone, password);
        //获取新的用户信息
        User user = registerMapper.getNewUser(phone);
        //完成candidate身份注册
        return registerMapper.candidateRegister(user.getId());
    }

    /**
     * 完善候选人的信息
     *
     * @param phone    用户的电话号码，作为唯一标识
     * @param name     用户的姓名
     * @param sex      用户的性别
     * @param birthday 用户的生日
     * @return 返回完善信息的操作结果
     */
    @Override
    public Integer perfectCandidateInfo(String phone, String name, Integer sex,
                                        Date birthday) {
        return registerMapper.perfectCandidateInfo(phone, name, sex, birthday);
    }
}
