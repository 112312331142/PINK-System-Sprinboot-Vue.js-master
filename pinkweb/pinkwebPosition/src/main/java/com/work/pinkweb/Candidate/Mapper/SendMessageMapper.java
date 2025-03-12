package com.work.pinkweb.Candidate.Mapper;

import com.work.pinkweb.Entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SendMessageMapper {


    // 根据r_id得出eHrid
    @Select("select ehrid from recruitment_info where r_id=#{rId}")
    Integer getEhrIdByRId(Integer rId);

    @Insert("insert into message(c_id,r_id,m_content,m_time,flag) values(#{cId},#{rId},#{message},#{now},#{flag})")
    void sendMessage(String cId, String rId, String message, LocalDateTime now, Integer flag);

    @Select("select m_content from message where c_id=#{cId} and r_id=#{rId}")
    List<String> getMessageById(String cId, String rId);

    @Select("select * from message where r_id=#{rId}")
    List<Message> getMessageByRId(Integer rId);

    // 根据id在user表中查找姓名
    @Select("select name from user where id=#{id}")
    String getNameById(Integer id);

    @Insert("insert into message(c_id,r_id,m_content,m_time,flag) values(#{cId},#{rId},#{message},#{now},#{flag})")
    void sendMessageHr(String cId, String rId, String message, LocalDateTime now, Integer flag);
}
