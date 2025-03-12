package com.work.pinkweb.Candidate.ServiceImpl;

import com.work.pinkweb.Candidate.Mapper.SendMessageMapper;
import com.work.pinkweb.Candidate.Service.SendMessageService;
import com.work.pinkweb.Entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    @Autowired
    private SendMessageMapper sendMessageMapper;


    @Override
    public void sendMessage(String cId, String rId, String message, Integer flag) {
        Integer ehrIdByRId = sendMessageMapper.getEhrIdByRId(Integer.parseInt(rId));
        sendMessageMapper.sendMessage(String.valueOf(cId), String.valueOf(ehrIdByRId), message, LocalDateTime.now(), flag);
    }

    @Override
    public List<String> getMessageById(String cId, String rId) {
        Integer ehrIdByRId = sendMessageMapper.getEhrIdByRId(Integer.parseInt(rId));
        return sendMessageMapper.getMessageById(cId, ehrIdByRId.toString());
    }

    @Override
    public List<Message> getMessageByRId(Integer rId) {
        return sendMessageMapper.getMessageByRId(rId);
    }

    @Override
    public String getNameById(Integer id) {
        return sendMessageMapper.getNameById(id);
    }

    @Override
    public void sendMessageHr(String cId, String rId, String message, Integer flag) {
        sendMessageMapper.sendMessageHr(String.valueOf(cId), String.valueOf(rId), message, LocalDateTime.now(), flag);
    }
}
