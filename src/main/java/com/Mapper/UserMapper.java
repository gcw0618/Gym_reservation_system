package com.Mapper;

import com.Entity.Picture;
import com.Entity.User;
import org.apache.ibatis.annotations.Mapper;
        import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserMapper {
    public boolean checklogin_email(String email,String password);//用户使用用户名登入
    public String get_username(String email);//根据邮箱获取用户名
    public boolean checkuser_email(String email);//检测注册的邮箱是否重复
    public boolean insertuser(String email,String username,String password);//用户注册
    public void updatepassword(String email,String username,String nowpassword,String password);//用户设置密码
    public User get_userdata(String email);//获取用户基本资料
    public boolean update_userdata(String email,String username,String phonenumber);//修改用户基本资料
    public ArrayList<Picture> gethomepic();//获取首页图片
    public boolean delete_my_appointment(String sitename,int sitenum,String start_time,String end_time,String email);//删除用户的预约
}
