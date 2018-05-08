package com.example.chl.campusnews.Model;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by chl on 2018/3/20.
 */

public class NewsRecyclerItemInfo {

    private String headIcon;
    private String name;
    private String question;
    private String content;
    private String agree;
    private String comment;
    private String time;

    public NewsRecyclerItemInfo() {
    }

    public NewsRecyclerItemInfo(String headIcon, String name, String content, String agree, String comment, String time) {
        this.headIcon = headIcon;
        this.name = name;
        this.content = content;
        this.agree = agree;
        this.comment = comment;
        this.time = time;
    }

    public NewsRecyclerItemInfo(String headIcon, String name, String question, String content, String agree, String comment, String time) {
        this.headIcon = headIcon;
        this.name = name;
        this.question = question;
        this.content = content;
        this.agree = agree;
        this.comment = comment;
        this.time = time;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
