package com.Controller;

import com.Entity.Picture;
import com.Entity.Site;
import com.Mapper.SiteMapper;
import com.Service.AdministratorService;
import com.Service.SiteService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SiteController {
    @Autowired
    SiteMapper siteMapper;
    @Autowired
    private SiteService siteService;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    static public String user_username;
    static public String user_password;
    static public String user_email;
    public String kind;
    public String user_sitename,user_date,start_time,end_time,admin_nowsitename;//预约信息
    public String add_site_pic_name,add_home_pic_name;//图片
    public int add_site_num,add_site_price;
    public int user_sitenum,user_siteprice,newsiteprice,paymoney,admin_nowsitenum;
    public String user_appoint_sitename;
    public int user_appoint_sitenum,user_appoint_siteprice;
    ArrayList<Site> time_list;
    
    //记录用户名密码
    public void getCustomerName(String email,String username,String password){
        user_email=email;
        user_username=username;
        user_password=password; 
    }

    //用户前往对应的场馆
    @RequestMapping("/user/site{sitename}")
    public String usersite(@RequestParam("sitename")String sitename, Model model){
        ArrayList<Site> site_list=siteService.find_site(sitename);
        model.addAttribute("site_list",site_list);
        return "user/site";
    }
    
    //获取场馆信息，前往选择时间
   @RequestMapping("/user/choosetime{sitename,sitenum,siteprice}")
    public String choosetime(@RequestParam("sitename")String name,@RequestParam("sitenum") int num,@RequestParam("siteprice") int price,HttpServletResponse response,Model model){
        response.setCharacterEncoding("utf-8");
        user_appoint_sitename=name;
        user_appoint_sitenum=num;
        user_appoint_siteprice=price;
        model.addAttribute("sitename",name);
        return "user/choosetime";
    }

    //用户选择日期查看可选择场地
    @RequestMapping("/user/get_appointment_time_by_time")
    public void get_appointment_time_by_time(@RequestParam("choosedate")String date,HttpServletResponse response,HttpServletRequest request, Model model) throws IOException{
        response.setCharacterEncoding("utf-8");
        JSONObject json=new JSONObject();
        ArrayList<Site> site_list=siteService.get_all_appointment_by_date(date, user_appoint_sitename, user_appoint_sitenum);
        ArrayList<Site> all_time_list=siteService.set_all_time();
        Map<String, Boolean> mp=siteService.set_map();
        user_date=date;
        for(int i=0;i<site_list.size();i++){
            mp.replace(site_list.get(i).getStart_time(),false);
           /* System.out.println(site_list.get(i).getSitename()+" "+site_list.get(i).getSitenum()+" "+site_list.get(i).getStart_time()+" "+site_list.get(i).getEnd_time());*/
        }
        for(int i=0;i<all_time_list.size();i++){
            int time_i=i+1;
            if(mp.get(all_time_list.get(i).getStart_time())){
                /*System.out.println("time_start_"+time_i);*/
                String time_start_to_end=all_time_list.get(i).getStart_time()+" ~ "+all_time_list.get(i).getEnd_time();
                json.put("time_start_"+time_i,all_time_list.get(i).getStart_time());
                json.put("time_end_"+time_i,all_time_list.get(i).getEnd_time());
                json.put("time_"+time_i,time_start_to_end);
                /*System.out.println(all_time_list.get(i).getStart_time()+" "+all_time_list.get(i).getEnd_time());*/
            }
            else{
                json.put("time_start_"+time_i,"不可选");
                json.put("time_end_"+time_i,"不可选");
                json.put("time_"+time_i,"不可选");
            }
        }
        model.addAttribute("sitename",user_appoint_sitename);
        model.addAttribute("appointment_btn","预定");
        response.getWriter().print(json);
        //return "user/choosetime";

    }
    
    //用户预订场地
    @RequestMapping("/user/appointment_site{start_time,end_time}")
    public String appointment_site(@RequestParam("start_time")String start_time,@RequestParam("end_time") String end_time,HttpServletResponse response,Model model){
        response.setCharacterEncoding("utf-8");
        UserController userController=new UserController();
        Site site=new Site();
        site.setSitename(user_appoint_sitename);
        site.setSitenum(user_appoint_sitenum);
        site.setEmail(user_email);
        site.setUsername(user_username);
        site.setSitedate(user_date);
        site.setStart_time(start_time);
        site.setEnd_time(end_time);
        boolean result=siteService.appoint_site(site);
        ArrayList<Site> site_list=siteService.find_site(user_appoint_sitename);
        model.addAttribute("site_list",site_list);
        return "user/site";
    }
    
    
    
    //---------------------------user和admin分割线---------------------------
    
    
    //管理员添加场地信息
    @RequestMapping("/admin/admin_add_site{sitenum,siteprice}")
    public String admin_add_site( @RequestParam("sitenum") int num, @RequestParam("siteprice") int price, HttpServletResponse response, Model model) throws IOException {
        response.setCharacterEncoding("utf-8");
        boolean result=siteService.admin_add_site(admin_nowsitename,num,price,add_site_pic_name);
        if (result){
            ArrayList<Site> site_list=siteService.find_site(admin_nowsitename);
            model.addAttribute("site_list",site_list);
        }
        return "admin/site";
    }
    
    //管理员修改场地信息
    @RequestMapping("/admin/admin_alert_site{sitenum,siteprice}")
    public String admin_alert_site(@RequestParam("sitenum") int sitenum,@RequestParam("siteprice") int siteprice,HttpServletResponse response,Model model){
        response.setCharacterEncoding("utf-8");
        boolean flag=siteService.admin_alert_site(admin_nowsitename,admin_nowsitenum,sitenum,siteprice,add_site_pic_name);
        ArrayList<Site> site_list=siteService.find_site(admin_nowsitename);
        model.addAttribute("site_list",site_list);
        return "admin/site";
    }

    //管理员删除场馆信息
    @RequestMapping("/admin/admin_delete_site{sitename,sitenum,siteprice}")
    public String admin_delete_site(@RequestParam("sitename")String name,@RequestParam("sitenum") int num,@RequestParam("siteprice") int price,HttpServletResponse response,Model model){
        response.setCharacterEncoding("utf-8");
        boolean flag=siteService.admin_delete_site(name,num);
        if (flag){
            ArrayList<Site> site_list=siteService.find_site(name);
            model.addAttribute("site_list",site_list);
        }
        return "admin/site";
    }

   
    //管理员进入编辑场地信息页面
    @RequestMapping("/admin/admin_select_site{sitename,sitenum,siteprice}")
    public String admin_select_site(@RequestParam("sitename")String name,@RequestParam("sitenum") int num,@RequestParam("siteprice") int price,HttpServletResponse response,Model model){
        response.setCharacterEncoding("utf-8");
        model.addAttribute("sitename",name);
        admin_nowsitename=name;
        admin_nowsitenum=num;
        return "admin/alertsite";
    }

    //管理员确认预约完成
    @RequestMapping("/admin/complete_user_appointment{email,username,sitename,sitenum,sitedate,start_time,end_time}")
    public String complete_user_appointment(@RequestParam("email") String email,@RequestParam("username")String username,@RequestParam("sitename") String sitename,@RequestParam("sitenum") int sitenum,@RequestParam("sitedate")String sitedate,@RequestParam("start_time") String start_time,@RequestParam("end_time") String end_time,HttpServletResponse response,Model model){
        response.setCharacterEncoding("utf-8");
        siteService.complete_user_appointment(email,username,sitename,sitenum,sitedate,start_time,end_time);
        ArrayList<Site> site_list=siteService.find_user_appointment();
        Map<String,String> mp=get_sitename_zh_map();
        for(int i=0;i<site_list.size();i++){
            site_list.get(i).setSitename_zh(mp.get(site_list.get(i).getSitename()));
        }
        model.addAttribute("site_list",site_list);
        return "admin/viewappointment";
    }

    //管理员删除预约记录
    @RequestMapping("/admin/delete_completed_user_appointment{email,username,sitename,sitenum,sitedate,start_time,end_time}")
    public String delete_completed_user_appointment(@RequestParam("email") String email,@RequestParam("username")String username,@RequestParam("sitename") String sitename,@RequestParam("sitenum") int sitenum,@RequestParam("sitedate")String sitedate,@RequestParam("start_time") String start_time,@RequestParam("end_time") String end_time,HttpServletResponse response,Model model){
        response.setCharacterEncoding("utf-8");
        siteService.delete_completed_user_appointment(email,username,sitename,sitenum,sitedate,start_time,end_time);
        ArrayList<Site> site_list=siteService.find_user_completed_appointment();
        Map<String,String> mp=get_sitename_zh_map();
        for(int i=0;i<site_list.size();i++){
            site_list.get(i).setSitename_zh(mp.get(site_list.get(i).getSitename()));
        }
        model.addAttribute("site_list",site_list);
        return "admin/viewcompletedappointment";
    }
    
    //前往对应的场馆
    @RequestMapping("/admin/site{sitename}")
    public String adminsite(@RequestParam("sitename")String sitename, Model model){
        ArrayList<Site> site_list=siteService.find_site(sitename);
        admin_nowsitename=sitename;//设置当前所在场馆
        model.addAttribute("site_list",site_list);
        return "admin/site";
    }

    //管理员查看待完成预约情况
    @RequestMapping("/admin/viewappointment")
    public String viewappointment( Model model){
        ArrayList<Site> site_list=siteService.find_user_appointment();
        Map<String,String> mp=get_sitename_zh_map();
        for(int i=0;i<site_list.size();i++){
            site_list.get(i).setSitename_zh(mp.get(site_list.get(i).getSitename()));
        }
        model.addAttribute("site_list",site_list);
        return "admin/viewappointment";
    }

    //管理员查看已完成预约情况
    @RequestMapping("/admin/view_completed_appointment")
    public String view_completed_appointment( Model model){
        ArrayList<Site> site_list=siteService.find_user_completed_appointment();
        Map<String,String> mp=get_sitename_zh_map();
        for(int i=0;i<site_list.size();i++){
            site_list.get(i).setSitename_zh(mp.get(site_list.get(i).getSitename()));
        }
        model.addAttribute("site_list",site_list);
        return "admin/viewcompletedappointment";
    }
    
    //管理员上传场地图片
    @RequestMapping("/uploadFile")
    @ResponseBody
    public JSON uploadFile(MultipartFile file, HttpServletRequest request) throws IOException {
        JSONObject json=new JSONObject();
        ApplicationHome ah = new ApplicationHome(getClass());

        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath() ;
        courseFile=courseFile+"/src/main/resources/static/images/";
        //System.out.println(courseFile);
        

        /*String filePath = "D:/idea project/Gym_reservation_system/src/main/resources/static/images/";*/
        File file1 = new File(courseFile);
        
        if (!file1.exists()) {
            file1.mkdirs();
        }
        add_site_pic_name="/images/"+file.getOriginalFilename().trim();
        String finalFilePath = courseFile + file.getOriginalFilename().trim();
        
        //System.out.println(finalFilePath);
        
        File desFile = new File(finalFilePath);
        if (desFile.exists()) {
            desFile.delete();
        }
        try {
            file.transferTo(desFile);
            json.put("code",0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            json.put("code",1);
        }
        return json;
    }
    
    
    //====================系统控制
    
    //获取场地中文名Map
    public Map<String,String> get_sitename_zh_map(){
        ArrayList<Site> site_list_zh=siteMapper.find_sitename_zh();
        Map<String,String> mp=new HashMap<>();
        for(int i=0;i<site_list_zh.size();i++){
            mp.put(site_list_zh.get(i).getSitename_en(),site_list_zh.get(i).getSitename_zh());
        }
        return mp;
    }

    //补全场馆信息
    public ArrayList<Site> set_site_inform(ArrayList<Site> site_list){
        ArrayList<Site> site_zh_list=siteService.find_sitename_zh();
        ArrayList<Site> site_pic_list=siteService.find_all_site();
        Map<String,String> site_zh_mp= new HashMap<String,String>();
       /* Map<String,Integer> site_pic1_mp=new HashMap<String, Integer>();
        Map<Map,String> site_pic_mp=new HashMap<Map, String>();
        for(int i=0;i<site_pic_list.size();i++){
            site_pic1_mp.put(site_pic_list.get(i).getSitename(),site_pic_list.get(i).getSitenum());
            site_pic_mp.put(site_pic1_mp,site_pic_list.get(i).getSitepic());
        }*/
        for(int i=0;i<site_zh_list.size();i++){
            site_zh_mp.put(site_zh_list.get(i).getSitename_en(),site_zh_list.get(i).getSitename_zh());
        }
        for (int i=0;i<site_list.size();i++){
            site_list.get(i).setSitename_zh(site_zh_mp.get(site_list.get(i).getSitename()));
        }
        return site_list;
    }
    
}
