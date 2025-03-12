package com.work.pinkweb.Entity;


import java.time.LocalDateTime;
import java.util.Date;

public class Message {

    private Integer m_id;
    private Integer c_id;
    private Integer r_id;
    private String m_content;
    private Date m_time;
    private Integer flag;

    public Message(Integer m_id, Integer c_id, Integer r_id, String m_content,
                   Date m_time, Integer flag) {
        this.m_id = m_id;
        this.c_id = c_id;
        this.r_id = r_id;
        this.m_content = m_content;
        this.m_time = m_time;
        this.flag = flag;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Message() {
    }

    public Integer getM_id() {
        return m_id;
    }

    public void setM_id(Integer m_id) {
        this.m_id = m_id;
    }

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public Integer getR_id() {
        return r_id;
    }

    public void setR_id(Integer r_id) {
        this.r_id = r_id;
    }

    public String getM_content() {
        return m_content;
    }

    public void setM_content(String m_content) {
        this.m_content = m_content;
    }

    public Date getM_time() {
        return m_time;
    }

    public void setM_time(Date m_time) {
        this.m_time = m_time;
    }
}
