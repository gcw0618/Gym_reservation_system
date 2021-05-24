package com.Controller;

import com.Entity.Picture;
import com.Entity.Site;
import com.Entity.User;
import com.Mapper.SiteMapper;
import com.Mapper.UserMapper;
import com.Service.SiteService;
import com.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    
    private int canregister_username=0;
    private int canregister_password=0;
    @Autowired
    private UserService userservice;
    @Autowired
    private SiteService siteService;
    public String nowusername,nowpassword,nowemail;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SiteMapper siteMapper;
    
    @RequestMapping("/user/login")
    public String login(){
        return "user/login";
    }
    
    //检测用户登入
    @RequestMapping("/checklogin")
    @ResponseBody
    public boolean checklogin(String email,String password) {
        SiteController siteController=new SiteController();
        boolean result=userservice.checklogin_email(email,password);
        if (result){
            nowemail=email;
            nowusername=userservice.get_username(email);
            nowpassword=password;
            siteController.getCustomerName(email,nowusername,password);
            return true; 
        }
        else{ return false; }
    }

    //用户注册
    @RequestMapping("/user/register")
    public String register(Model model,HttpServletRequest request){
        return "user/register";
    }
    
    //用户主界面
    @RequestMapping("/user/index")
    public String index(Model model,HttpServletRequest request){
        ArrayList<Site> sitename_list=siteService.find_sitename_zh();
        model.addAttribute("sitename_list",sitename_list);
        model.addAttribute("username",nowusername);
        return "user/index";
    }

    //用户基本资料界面
    @RequestMapping("/user/userdata")
    public String userdata(Model model){
        User user=new User();
        user=userservice.get_userdata(nowemail);
        model.addAttribute("user",user);
        return "user/userdata"; 
    }

    //用户修改基本资料界面
    @RequestMapping("/user/alter_data")
    public String alter_data(String username,String phonenumber,Model model){
        User user=new User();
        boolean alert_result=userservice.update_userdata(nowemail,username,phonenumber);
        user=userservice.get_userdata(nowemail);
        model.addAttribute("user",user);
        return "user/userdata";
    }
    
    //用户欢迎界面
    @RequestMapping("/user/home")
    public String home(Model model){
        ArrayList<Picture> pic_list=userservice.gethomepic();
        model.addAttribute("pic_list",pic_list);
        return "user/home"; 
    }
    
    //检测注册的用户名是否重复，若不重复则注册
    @RequestMapping("/checkregister")
    @ResponseBody
    public boolean checkregister(String email,String username,String password){
        if(userservice.checkuser_email(email)){
            canregister_username=1;
            nowemail=email;
            nowusername=username;
            nowpassword=password;
            userservice.insertuser(email,username,password);
            return true;
        }
        else{
            canregister_username=0;
            return false;
        }
    }
    
    //修改密码页面
    @RequestMapping("/user/changepassword")
    public String changepassword(){
        return "user/changepassword";
    }

    //进行修改密码操作
    @RequestMapping("/user/gochangepassword")
    @ResponseBody
    public boolean gochangepassword(String password1){
        userservice.updatepassword(nowemail,nowusername,nowpassword,password1);
        nowpassword=password1;
        return true;
    }
    
    //查看待完成的预约
    @RequestMapping("/user/myappointment")
    public String myappointment(Model model){ 
        ArrayList<Site> my_appointment_list=siteService.viewmyappointment(nowemail);
        my_appointment_list=set_site_inform(my_appointment_list);
        model.addAttribute("appointment_list",my_appointment_list);
        return "user/myappointment"; 
    }

    //查看已完成的预约
    @RequestMapping("/user/my_completed_appointment")
    public String my_completed_appointment(Model model){
        ArrayList<Site> my_appointment_list=siteService.view_my_completed_appointment(nowemail);
        my_appointment_list=set_site_inform(my_appointment_list);
        model.addAttribute("appointment_list",my_appointment_list);
        return "user/completedappointment";
    }
    
    //删除预约操作
    @RequestMapping("/user/delete_my_appointment{sitename,sitenum,start_time,end_time}")
    public String delete_my_appointment(@RequestParam("sitename")String sitename,@RequestParam("sitenum") int sitenum,@RequestParam("start_time") String start_time,@RequestParam("end_time") String end_time,Model model){
        boolean result=userservice.delete_my_appointment(sitename,sitenum,start_time,end_time,nowemail);
        ArrayList<Site> my_appointment_list=siteService.viewmyappointment(nowemail);
        my_appointment_list=set_site_inform(my_appointment_list);
        model.addAttribute("appointment_list",my_appointment_list);
        return "user/myappointment";
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
