package com.work.pinkweb.Candidate.Controller;

import com.work.pinkweb.Entity.Candidate;
import com.work.pinkweb.Entity.User;
import com.work.pinkweb.Candidate.Service.EditService;
import com.work.pinkweb.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

//import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 控制器类，处理与编辑候选人信息相关的请求
 */
@RestController
public class EdiController {
    @Autowired
    private EditService editService;

    /**
     * 根据候选人ID获取候选人信息
     *
     * @param c_id 候选人的唯一标识符
     * @return 返回Candidate对象，包含候选人详细信息
     */
    @RequestMapping(value = "/index/apply_edit/{c_id}", method = RequestMethod.GET)
    public Candidate getCandidateInfo(@PathVariable Integer c_id) {
        return editService.getCandidateInfo(c_id);
    }

    /**
     * 修改用户个人信息
     *
     * @param user 包含用户信息的User对象
     * @return 返回修改结果或状态
     */
    @PostMapping("/index/apply_edit/modify_personal_info")
    public Object modifyUserInfo(@RequestBody User user) {
        return editService.modifyUserInfo(user);
    }

    /**
     * 修改候选人个人信息
     *
     * @param candidate 包含候选人信息的Candidate对象
     * @return 返回修改结果或状态
     */
    @PostMapping("/index/apply_edit/modify_personal_candidate_info")
    public Object modifyCandidateInfo(@RequestBody Candidate candidate) {
        return editService.modifyCandidateInfo(candidate);
    }

    /**
     * 上传用户头像
     *
     * @param file 包含头像数据的MultipartFile对象
     * @param c_id 候选人的唯一标识符
     * @return 返回上传后的头像路径或其他信息
     * @throws IOException 当文件上传过程中发生I/O错误时抛出
     */
    @PostMapping("/index/apply_profile/updateAvatar")
    public String avatarUpdate(@RequestParam("file") MultipartFile file,
                               @RequestParam(value = "c_id") Integer c_id) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = FileUtil.getUpLoadFilePath("static/avatar/");
        return editService.updateAvatar(c_id, fileName, filePath + "/" + c_id.toString(),
                new Date(), file);
    }

    /**
     * 初始化数据绑定器，用于将请求中的数据绑定到模型中
     *
     * @param binder WebDataBinder对象，用于设置数据绑定规则
     * @param request WebRequest对象，包含请求相关的信息
     */
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期 注意这里的转化要和传进来的字符串的格式一直 如2015-9-9 就应该为yyyy-MM-dd
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }
}
