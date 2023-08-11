package com.worldpeak.chnsmilead.test;

import java.io.Serializable;

public class ContactBean implements Serializable {
    private int iconId;
    private String title;
    private String phoneNum;
    private String firstHeadLetter;
    public ContactBean(int iconId, String title, String phoneNum, String firstHeadLetter) {
        this.iconId = iconId;
        this.title = title;
        this.phoneNum = phoneNum;
        this.firstHeadLetter=firstHeadLetter;
    }
    public ContactBean() {
    }
    public int getIconId() {
        return iconId;
    }
    public String getFirstHeadLetter() {
        return firstHeadLetter;
    }
    public void setFirstHeadLetter(String firstHeadLetter) {
        this.firstHeadLetter = firstHeadLetter;
    }
    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    @Override
    public String toString() {
        return "ContactBean{" +
                "iconId=" + iconId +
                ", title='" + title + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", firstHeadLetter='" + firstHeadLetter + '\'' +
                '}';
    }
}