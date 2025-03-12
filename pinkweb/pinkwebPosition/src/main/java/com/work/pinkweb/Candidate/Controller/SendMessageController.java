package com.work.pinkweb.Candidate.Controller;

import com.work.pinkweb.Candidate.Service.SendMessageService;
import com.work.pinkweb.Entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class SendMessageController {

    @Autowired
    private SendMessageService sendMessageService;

    // 新增方法：接收消息
    @PostMapping("/send")
    public String sendMessage(@RequestBody Map<String, Object> sendMessage) {
        Map<String, String> data = (Map<String, String>) sendMessage.get("data");
        String c_id = data.get("u_id");
        String r_id = (String) data.get("p_id");
        String message = data.get("message");

        System.out.println("Received message from user " + c_id + " to project " +
                r_id + ": " + message);
        sendMessageService.sendMessage(c_id, r_id, message, 0);
        // 处理接收到的消息

        return "Message received successfully";
    }

    // 查看消息
    @GetMapping("/get")
    public List<String> getMessageById(String u_id, String p_id) {
        return sendMessageService.getMessageById(u_id, p_id);
    }

    // hr返回消息
    @GetMapping("/getHr")
    @ResponseBody
    public List<Message> getMessageByRId(Integer r_id) {
        List<Message> messageByRId = sendMessageService.getMessageByRId(r_id);
        return messageByRId;
    }

    @GetMapping("/getName")
    public String getNameById(Integer id) {
        String nameById = sendMessageService.getNameById(id);
        return nameById;
    }

    // 新增方法：接收消息
    @PostMapping("/sendHr")
    public String sendMessageHr(@RequestBody Map<String, Object> sendMessage) {
        Map<String, String> data = (Map<String, String>) sendMessage.get("data");
        System.out.println(data.get("u_id"));
        String c_id = data.get("u_id").toString();
        String r_id = data.get("p_id").toString();
        String message = data.get("message");

        System.out.println("Received message from user " + c_id + " to project " +
                r_id + ": " + message);
        sendMessageService.sendMessageHr(c_id, r_id, message, 1);
        // 处理接收到的消息

        return "Message received successfully";
    }

}
