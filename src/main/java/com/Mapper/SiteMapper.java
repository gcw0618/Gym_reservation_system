package com.Mapper;

import com.Entity.Site;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public interface SiteMapper {
    public ArrayList<Site> find_site(String kind);//查找篮球场的所有场地
    public ArrayList<Site> find_all_site();//获取所有场地信息
    public ArrayList<Site> get_time(Site site);//查找场地的所有已预约时间
    public void appoint_site(Site site);//用户预约场地
    public ArrayList<Site> view_my_appointment(String email);//获取用户待完成的预约
    public ArrayList<Site> view_my_completed_appointment(String email);//查看用户已完成的预约
    public ArrayList<Site> find_user_appointment();//管理员获取所有用户待完成的预约
    public ArrayList<Site> find_user_completed_appointment();//管理员获取所有用户已完成的预约
    public ArrayList<Site> getnotice();//获取公告通知
    public ArrayList<Site> find_sitename_zh();//在sitename中查找场地中文名
    public boolean admin_delete_site(String sitename,int sitenum);//管理员删除场地
    public boolean admin_alert_site(String admin_nowsitename,int admin_nowsitenum,int sitenum,int siteprice,String sitepic);//管理员修改场地
    public boolean admin_add_site(String sitename,int sitenum,int siteprice,String sitepic);//管理员添加场地
    public ArrayList<Site> get_all_appointment_by_date(String sitedate,String sitename,int sitenum);//根据日期选择已被预约场地时间
    public boolean addgym(String sitename_zh,String sitename_en);//管理员添加场馆
    public boolean deletegym(String sitename_zh,String sitename_en);//管理员删除场馆
    public void complete_user_appointment(String email,String username,String sitename,int sitenum,String sitedate,String start_time,String end_time);//管理员确认预约完成
    public void delete_completed_user_appointment(String email,String username,String sitename,int sitenum,String sitedate,String start_time,String end_time);//管理员删除预约记录
}
