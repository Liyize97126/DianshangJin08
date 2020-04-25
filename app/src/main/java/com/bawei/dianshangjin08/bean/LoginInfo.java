package com.bawei.dianshangjin08.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 用户信息
 */
@Entity
public class LoginInfo {
    private String headPic;
    private String nickName;
    private String phone;
    private String sessionId;
    private int sex;
    //登录状态标识：0为未登录，1为已登录
    private int status;
    @Id
    private long userId;
    @Generated(hash = 1637525183)
    public LoginInfo(String headPic, String nickName, String phone,
            String sessionId, int sex, int status, long userId) {
        this.headPic = headPic;
        this.nickName = nickName;
        this.phone = phone;
        this.sessionId = sessionId;
        this.sex = sex;
        this.status = status;
        this.userId = userId;
    }
    @Generated(hash = 1911824992)
    public LoginInfo() {
    }
    public String getHeadPic() {
        return headPic;
    }
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public int getSex() {
        return sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
