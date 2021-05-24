package com.Service;


import com.Entity.Picture;
import com.Entity.Site;
import com.Mapper.AdminMapper;
import com.Mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdministratorService {
    @Autowired
    AdminMapper adminMapper;
    
    //检查管理员登录
    public boolean checkadminlogin(String username,String password){
        boolean result= adminMapper.checkadminlogin(username,password);
        if(result) return true;
        else return false;
    }
    
    //管理员修改密码
    public boolean updatepassword(String username,String password){
        return adminMapper.updatepassword(username,password);
    }
    
    //管理员获取主界面图片
    public ArrayList<Picture> get_index_pic(){
        ArrayList<Picture> pic=adminMapper.get_index_pic();
        return pic;
    }
    
    //添加主界面图片
    public boolean add_home_pic(String picture){
        return adminMapper.add_home_pic(picture);
    }
    
    //删除主界面图片
    public boolean delete_home_pic(String picture){
        return adminMapper.delete_home_pic(picture);
    }
}
