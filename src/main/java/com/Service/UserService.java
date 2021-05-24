package com.Service;

import com.Entity.Picture;
import com.Entity.User;
import com.Mapper.SiteMapper;
import com.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    
    //用户使用邮箱登入检测
    public boolean checklogin_email(String email,String password){
        boolean result=userMapper.checklogin_email(email,password);
        if(result) return true;
        else return false;
    }
    
    //获取用户名
    public String get_username(String email){
        String username=userMapper.get_username(email);
        return username;
    }
    
    //检测注册的用户名是否重复
    public boolean checkuser_email(String email){
        boolean result=userMapper.checkuser_email(email);
        if (result) return false;
        else return true;
    }
    
    //用户注册
    public void insertuser(String email,String username,String password){ userMapper.insertuser(email,username,password); }
    
    //用户重置密码
    public void updatepassword(String email,String username,String nowpassword,String password){
        userMapper.updatepassword(email,username,nowpassword,password);
    }
    
    //获取用户资料
    public User get_userdata(String email){
        User user=new User();
        user=userMapper.get_userdata(email);
        return user;
    }

    //获取更新用户资料
    public boolean update_userdata(String email,String username,String phonenumber){
        boolean result=userMapper.update_userdata(email,username,phonenumber);
        return result;
    }
    
    //获取home界面图片
    public ArrayList<Picture> gethomepic(){
        ArrayList<Picture> pic_list=userMapper.gethomepic();
        return pic_list;
    }
    
    //删除用户待完成的预约
    public boolean delete_my_appointment(String sitename,int sitenum,String start_time,String end_time,String email){
        boolean result=userMapper.delete_my_appointment(sitename,sitenum,start_time,end_time,email);
        return true;
    }
    
}
