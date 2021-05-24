package com.Service;

import com.Entity.Site;
import com.Mapper.SiteMapper;
import com.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SiteService {
    @Autowired
    SiteMapper siteMapper;
    UserMapper userMapper;
    UserService userService;
    
    //查找场地的所有已预约时间
    public ArrayList<Site> checktime(Site site){
        ArrayList<Site> time_list=siteMapper.get_time(site);
        return time_list;
    }
    
    //设置订单id并预约场地
    public boolean appoint_site(Site site){
        String indexname=getStringRandom(10);
        site.setIndexname(indexname);
        siteMapper.appoint_site(site);
        return true;
    }
    
    //查找某场馆的所有场地
    public ArrayList<Site> find_site(String kind){
        ArrayList<Site> site_list=siteMapper.find_site(kind);
        return site_list;
    }

    //查找所有场地
    public ArrayList<Site> find_all_site(){
        ArrayList<Site> site_list=siteMapper.find_all_site();
        return site_list;
    }
    
    //根据日期选择场地已被预约时间
    public ArrayList<Site> get_all_appointment_by_date(String date,String sitename,int sitenum){
        ArrayList<Site> site_time_list=siteMapper.get_all_appointment_by_date(date,sitename,sitenum);
        return site_time_list;
    }
    
    
    //用户获取自己待完成的预约
    public ArrayList<Site> viewmyappointment(String email){
        ArrayList<Site> site_list=siteMapper.view_my_appointment(email);
        return site_list;
    }

    //用户查看用户已完成的预约
    public ArrayList<Site> view_my_completed_appointment(String email){
        ArrayList<Site> site_list=siteMapper.view_my_completed_appointment(email);
        return site_list;
    }
    
    //======================系统=====================
    
    //系统初始化Map
    public Map<String,Boolean> set_map(){
        Map<String,Boolean> mp = new HashMap<>();
        for(int i=7;i<=21;i++){
            String time=i+""+":00:00";
            mp.put(time,true);
        }
        return mp;
    }

    //生成随机数字和字母,
    public String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    //系统设定预定时间列表
    public ArrayList<Site> set_all_time(){
        ArrayList<Site> list=new ArrayList<>();
        for(int i=7;i<=21;i++){
            Site site=new Site();
            site.setStart_time(i+""+":00:00");
            site.setEnd_time(i+1+""+":00:00");
            list.add(site);
        }
        return list;
    }
    
    //获取公告须知通知
    public ArrayList<Site> getnotice(){
        ArrayList<Site> notice_list=siteMapper.getnotice();
        return notice_list;
    }
    
    //获取所有场地的中文名
    public ArrayList<Site> find_sitename_zh(){
        ArrayList<Site> sitename_zh_list=siteMapper.find_sitename_zh();
        return sitename_zh_list;
    }

    
    //=======================管理员=============
    
    //管理员查看所有用户待完成的预约
    public ArrayList<Site> find_user_appointment(){
        ArrayList<Site> site_list=siteMapper.find_user_appointment();
        return site_list;
    }

    //管理员查看所有用户已完成的预约
    public ArrayList<Site> find_user_completed_appointment(){
        ArrayList<Site> site_list=siteMapper.find_user_completed_appointment();
        return site_list;
    }
    
    //管理员修改场地信息
    public boolean admin_alert_site(String admin_nowsitename,int admin_nowsitenum,int sitenum,int siteprice,String sitepic){
        if(siteMapper.admin_alert_site(admin_nowsitename,admin_nowsitenum,sitenum,siteprice,sitepic)){ return true; }
        else{ return false; }
    }
    
    //管理员删除场馆信息
    public boolean admin_delete_site(String sitename,int sitenum){
        if(siteMapper.admin_delete_site(sitename,sitenum)){ return true; }
        else{ return false; }
    }
    
    //管理员添加场地信息
    public boolean admin_add_site(String sitename,int sitenum,int siteprice,String sitepic){
        if(siteMapper.admin_add_site(sitename,sitenum,siteprice,sitepic)){ return true; }
        else{ return false; }
    }
    
    //管理员添加场馆名
    public boolean addgym(String sitename_zh,String sitename_en){
        if (siteMapper.addgym(sitename_zh,sitename_en)){ return true; }
        else{ return false; }
    }

    //管理员删除场馆名
    public boolean deletegym(String sitename_zh,String sitename_en){
        if (siteMapper.deletegym(sitename_zh,sitename_en)){ return true; }
        else{ return false; }
    }
    
    //管理员确认预约完成
    public void complete_user_appointment(String email,String username,String sitename,int sitenum,String sitedate,String start_time,String end_time){
        siteMapper.complete_user_appointment(email,username,sitename,sitenum,sitedate,start_time,end_time);
    }

    //管理员删除预约记录
    public void delete_completed_user_appointment(String email,String username,String sitename,int sitenum,String sitedate,String start_time,String end_time){
        siteMapper.delete_completed_user_appointment(email,username,sitename,sitenum,sitedate,start_time,end_time);
    }
}
