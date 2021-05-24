package com.Mapper;

import com.Entity.Picture;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AdminMapper {
    public boolean checkadminlogin(String username, String password);//管理员登入
    public boolean updatepassword(String username,String password);//修改密码
    public ArrayList<Picture> get_index_pic();//获取主界面图片
    public boolean add_home_pic(String picture);//添加主界面图片
    public boolean delete_home_pic(String picture);//删除主界面图片
}
