package com.work.pinkweb.HR.Controller;

import com.work.pinkweb.Entity.User;
import com.work.pinkweb.HR.Service.UserService;
import com.work.pinkweb.utils.FileUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户头像上传接口
     *
     * @param request HttpServletRequest对象，用于获取请求信息
     * @param file    MultipartFile对象，代表上传的文件
     * @param uid     Integer类型，员工ID
     * @return 返回字符串表示上传结果
     * <p>
     * 该方法接收一个文件和员工ID，将文件作为员工头像进行上传和保存，并更新员工的头像信息
     */
    @PostMapping("/hire/hire_profile/avatar")
    public String regUser(HttpServletRequest request, @RequestParam("file") MultipartFile file,
                          @RequestParam("ehrid") Integer uid) {

        // 打印接收到的员工ID，用于调试
        System.out.println(uid);

        // 1.保存到硬盘上
        // 获取文件原始名称
        String fileName = file.getOriginalFilename();
        // 获取文件保存路径
        String filePath = FileUtil.getUpLoadFilePath("static/hrAvatar/");
        // 使用当前时间加上原始文件名，避免重名
        fileName = System.currentTimeMillis() + fileName;

        // 尝试将文件上传到指定路径
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // 如果上传过程中发生异常，打印异常信息
            e.printStackTrace();
        }

        // 记录创建时间
        Date created_time = new Date();

        // 根据员工ID更新数据库中的头像文件名
        userService.updateAvatarById(uid, fileName);
        // 打印更新后的用户信息，用于调试
        System.err.println(userService.getUserById(uid));

        // 返回上传成功消息
        return "上传成功";
    }
}
