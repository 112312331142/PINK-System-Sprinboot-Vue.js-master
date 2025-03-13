package com.work.pinkweb.Candidate.Controller;

import com.work.pinkweb.Candidate.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sendEmail")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request) {
        try {
            System.out.println(request.getTo());
            System.out.println(request.getSubject());
            System.out.println(request.getText());
            emailService.sendEmail(request.getTo(), request.getSubject(), request.getText());
            return ResponseEntity.ok().body(new Response(true, "邮件发送成功！"));
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResponseEntity.badRequest().body(new Response(false,
                    "邮件发送失败，请稍后重试。"));
        }
    }

    // 请求体类
    static class EmailRequest {
        private String to;
        private String subject;
        private String text;

        // Getters and Setters
        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    // 响应类
    static class Response {
        private boolean success;
        private String message;

        public Response(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        // Getters and Setters
        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
