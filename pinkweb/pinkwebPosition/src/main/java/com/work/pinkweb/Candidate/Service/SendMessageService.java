package com.work.pinkweb.Candidate.Service;

import com.work.pinkweb.Entity.Message;

import java.util.List;

public interface SendMessageService {
    void sendMessage(String uId, String pId, String message, Integer flag);

    List<String> getMessageById(String cId, String rId);

    List<Message> getMessageByRId(Integer rId);

    String getNameById(Integer id);

    void sendMessageHr(String cId, String rId, String message,Integer flag);

}
