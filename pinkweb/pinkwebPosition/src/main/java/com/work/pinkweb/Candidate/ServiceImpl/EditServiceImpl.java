package com.work.pinkweb.Candidate.ServiceImpl;

import com.work.pinkweb.Entity.Candidate;
import com.work.pinkweb.Entity.User;
import com.work.pinkweb.Candidate.Mapper.EditMapper;
import com.work.pinkweb.Candidate.Service.EditService;
import com.work.pinkweb.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 实现编辑服务的类，提供用户和候选人信息的修改功能
 */
@Service
public class EditServiceImpl implements EditService {
    // 注入编辑映射器，用于执行数据库操作
    @Autowired
    private EditMapper editMapper;

    /**
     * 获取候选人信息
     *
     * @param id 候选人的标识符
     * @return 候选人对象，包含所查询的信息
     */
    @Override
    public Candidate getCandidateInfo(Integer id) {
        return editMapper.getCandidateInfo(id);
    }

    /**
     * 修改用户信息
     *
     * @param user 用户对象，包含更新后的信息
     * @return 受影响的行数，表示修改操作的结果
     */
    @Override
    public Integer modifyUserInfo(User user) {
        return editMapper.modifyUserInfo(user);
    }

    /**
     * 修改候选人信息
     *
     * @param candidate 候选人对象，包含更新后的信息
     * @return 受影响的行数，表示修改操作的结果
     */
    @Override
    public Integer modifyCandidateInfo(Candidate candidate) {
        return editMapper.modifyCandidateInfo(candidate);
    }

    /**
     * 更新用户头像
     *
     * @param uid 用户标识符
     * @param name 文件名
     * @param url 存储路径
     * @param created_time 文件创建时间
     * @param file 要上传的文件
     * @return 操作结果的字符串表示
     */
    @Override
    public String updateAvatar(Integer uid, String name, String url, Date created_time, MultipartFile file) {
        try {
            // 执行文件上传操作
            FileUtil.uploadFile(file.getBytes(), url, name);
        } catch (Exception e) {
            // 打印异常信息，以便调试和日志记录
            e.printStackTrace();
        }
        // 更新数据库中的头像信息
        editMapper.avatarUpdate(uid, name);
        return "updateAvatar-success";
    }

}
