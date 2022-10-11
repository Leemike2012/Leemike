package com.the.electricdoor.Entity;

import java.util.Date;

public class Acount {
    private String acount_name;
    private String acount_password;
    private String nick_name;
    private int sex;
    private Date create_time;
    public String getAcount_name() {
        return acount_name;
    }
    public void setAcount_name(String acount_name) {
        this.acount_name = acount_name;
    }
    public String getAcount_password() {
        return acount_password;
    }
    public void setAcount_password(String acount_password) {
        this.acount_password = acount_password;
    }
    public String getNick_name() {
        return nick_name;
    }
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
    public int getSex() {
        return sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public Date getCreate_time() {
        return create_time;
    }
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
