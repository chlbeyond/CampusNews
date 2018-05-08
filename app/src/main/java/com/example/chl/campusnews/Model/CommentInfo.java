package com.example.chl.campusnews.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

/*评论id
    评论者id
	评论者名字
	评论内容
	被回复者名字
	被回复者id*/
public class CommentInfo {

    private String shuo_user_id; //发说说者id
    private String commentId;  //评论id
    private String shuoId;  //说说id
    private String user_id_comment;  //评论者id
    private String content_comment;  //评论内容
    private String to_user_comment;  //被回复者名字
    private String created_comment;  //评论时间戳
    private String user_nickname_comment;  //评论者名字
    private String to_user_id_comment; //被回复者id

    public CommentInfo() {
    }

    public CommentInfo(String shuo_user_id, String commentId, String shuoId, String user_id_comment, String user_nickname_comment,
                       String to_user_id_comment, String created_comment, String to_user_comment, String content_comment) {
        this.shuo_user_id = shuo_user_id;
        this.commentId = commentId;
        this.shuoId = shuoId;
        this.user_id_comment = user_id_comment;
        this.user_nickname_comment = user_nickname_comment;
        this.to_user_id_comment = to_user_id_comment;
        this.created_comment = created_comment;
        this.to_user_comment = to_user_comment;
        this.content_comment = content_comment;
    }

    public String getShuo_user_id() {
        return shuo_user_id;
    }

    public void setShuo_user_id(String shuo_user_id) {
        this.shuo_user_id = shuo_user_id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getShuoId() {
        return shuoId;
    }

    public void setShuoId(String shuoId) {
        this.shuoId = shuoId;
    }

    public String getUser_id_comment() {
        return user_id_comment;
    }

    public void setUser_id_comment(String user_id_comment) {
        this.user_id_comment = user_id_comment;
    }

    public String getContent_comment() {
        return content_comment;
    }

    public void setContent_comment(String content_comment) {
        this.content_comment = content_comment;
    }

    public String getTo_user_comment() {
        return to_user_comment;
    }

    public void setTo_user_comment(String to_user_comment) {
        this.to_user_comment = to_user_comment;
    }

    public String getCreated_comment() {
        return created_comment;
    }

    public void setCreated_comment(String created_comment) {
        this.created_comment = created_comment;
    }

    public String getUser_nickname_comment() {
        return user_nickname_comment;
    }

    public void setUser_nickname_comment(String user_nickname_comment) {
        this.user_nickname_comment = user_nickname_comment;
    }

    public String getTo_user_id_comment() {
        return to_user_id_comment;
    }

    public void setTo_user_id_comment(String to_user_id_comment) {
        this.to_user_id_comment = to_user_id_comment;
    }
}
