package com.example.chl.campusnews.Model;

import java.io.Serializable;

public class PersonInfo {

    private String id;
    private String headIcon;
    private int UserId;
    private String name;
    private String pinyin;  //名字拼音
    private boolean selected = false;

    private String nickName;
    private String distance;

    public String userimg;
    public PersonInfo() {}

    public PersonInfo(String id, String headIcon, String name, String pinyin) {
        this.id = id;
        this.headIcon = headIcon;
        this.name = name;
        this.pinyin = pinyin;
    }

    public String getId() {
        return id;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getUserId() {

        return UserId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setUserimg(String userimg){
        this.userimg = userimg;
    }
    public String getUserimg(){
        return this.userimg;
    }
}
