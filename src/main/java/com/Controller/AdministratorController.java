package com.Controller;

import com.Entity.Picture;
import com.Entity.Site;
import com.Mapper.AdminMapper;
import com.Mapper.SiteMapper;
import com.Mapper.UserMapper;
import com.Service.AdministratorService;
import com.Service.SiteService;
import com.Service.UserService;
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
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private SiteService siteService;
    public String nowusername_admin,nowpassword;
    public String add_home_pic_name;//图片
    @Autowired
    UserMapper userMapper;
    @Autowired
    SiteMapper siteMapper;
    @Autowired
    AdminMapper adminMapper;

    //管理员修改密码页面
    @RequestMapping("/admin/changepassword")
    public String changepassword() {return "/admin/changepassword";}

    //检测管理员登入
    @RequestMapping("/checkadminlogin")
    @ResponseBody
    public boolean checkadminlogin(String username,String password) {
        boolean result=administratorService.checkadminlogin(username,password);
        if (result){
            nowusername_admin=username;
            return true;
        }
        else{ return false; }
    }
    
    //进行修改密码操作
    @RequestMapping("/admin/gochangepassword")
    @ResponseBody
    public boolean gochangepassword(String password1){
        administratorService.updatepassword(nowusername_admin,password1);
        nowpassword=password1;
        return true;
    }

    //管理员主界面
    @RequestMapping("/admin/index")
    public String index(Model model, HttpServletRequest request){
        ArrayList<Site> sitename_list=siteService.find_sitename_zh();
        model.addAttribute("sitename_list",sitename_list);
        model.addAttribute("username",nowusername_admin);
        return "admin/index";
    }


    //管理员欢迎页面
    @RequestMapping("/admin/home")
    public String home(Model model) {
        ArrayList<Picture> pic=administratorService.get_index_pic();
        model.addAttribute("pic_list",pic);
        return "/admin/home";
    }
    
    //管理员登入页面
    @RequestMapping("/admin/login")
    public String administrator() {return "/admin/login";}
    
    
    //管理员获取界面图片
    @RequestMapping("/admin/go_admin_pic")
    public String go_adminpic(Model model){
        ArrayList<Picture> pic=administratorService.get_index_pic();
        model.addAttribute("pic_list",pic);
        return "admin/adminpic";
    }

    //管理员前往编辑界面图片
    @RequestMapping("/admin/admin_pic")
    public String adminpic(Model model){
        return "admin/admin_pic";
    }
    
    //管理员添加主界面图片
    @RequestMapping("/admin/admin_add_home_pic")
    public String admin_add_home_pic(HttpServletResponse response, Model model) throws IOException {
        response.setCharacterEncoding("utf-8");
        boolean result=administratorService.add_home_pic(add_home_pic_name);
        ArrayList<Picture> pic=administratorService.get_index_pic();
        model.addAttribute("pic_list",pic);
        return "admin/home";
    }
    
    //管理员删除主界面图片
    @RequestMapping("/admin/admin_delete_home_pic{picture}")
    public String admin_delete_home_pic(@RequestParam("picture")String picture,HttpServletResponse response, Model model) throws IOException {
        response.setCharacterEncoding("utf-8");
        boolean result=administratorService.delete_home_pic(picture);
        ArrayList<Picture> pic=administratorService.get_index_pic();
        model.addAttribute("pic_list",pic);
        return "admin/home";
    }

    //管理员编辑场馆信息界面
    @RequestMapping("/admin/admingym")
    public String admingym(Model model){
        ArrayList<Site> sitename_list=siteService.find_sitename_zh();
        model.addAttribute("sitename_list",sitename_list);
        return "admin/admingym";
    }
    
    //管理员删除场馆
    @RequestMapping("/admin/deletegym")
    public String deletegym(@RequestParam("sitename_zh")String sitename_zh,@RequestParam("sitename_en")String sitename_en,Model model, HttpServletRequest request){
        boolean result=siteService.deletegym(sitename_zh,sitename_en);
        if (result){
            ArrayList<Site> sitename_list=siteService.find_sitename_zh();
            model.addAttribute("sitename_list",sitename_list);
            return "admin/admingym";
        }
        else{
            return "admin/admingym";
        }
    }

    
    //管理员添加场馆
    @RequestMapping("/admin/addgym")
    @ResponseBody
    public String addgym(String sitename_zh,String sitename_en,Model model){
        siteService.addgym(sitename_zh,sitename_en);
        ArrayList<Site> sitename_list=siteService.find_sitename_zh();
        model.addAttribute("sitename_list",sitename_list);
        return "admin/admingym";
    }
    
    //管理员上传主界面图片
    @RequestMapping("/uploadhomepic")
    @ResponseBody
    public JSON uploadhomepic(MultipartFile file, HttpServletRequest request) throws IOException {
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
        add_home_pic_name="/images/"+file.getOriginalFilename().trim();
        String finalFilePath = courseFile + file.getOriginalFilename().trim();

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
    
}
