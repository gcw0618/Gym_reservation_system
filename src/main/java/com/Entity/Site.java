package com.Entity;

import java.io.Serializable;

public class Site implements Serializable {
    private String indexname;
    private String username;
    private String sitename;
    private String email;
    private int siteprice;
    private int sitenum;
    private String sitedate;
    private String start_time;
    private String end_time;
    private String sitepic;
    private String notice;
    private String sitename_zh;
    private String sitename_en;

    public String getIndexname() { return indexname; }
    public void setIndexname(String indexname) { this.indexname = indexname; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSitename() { return sitename; }
    public void setSitename(String sitename) { this.sitename = sitename; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getSiteprice() { return siteprice; }
    public void setSiteprice(int siteprice) { this.siteprice = siteprice; }

    public int getSitenum() { return sitenum; }
    public void setSitenum(int sitenum) { this.sitenum = sitenum; }

    public String getSitedate() { return sitedate; }
    public void setSitedate(String sitedate) { this.sitedate = sitedate; }

    public String getStart_time() { return start_time; }
    public void setStart_time(String start_time) { this.start_time = start_time; }

    public String getEnd_time() { return end_time; }
    public void setEnd_time(String end_time) { this.end_time = end_time; }

    public String getSitepic() { return sitepic; }
    public void setSitepic(String sitepic) { this.sitepic = sitepic; }

    public String getNotice() { return notice; }
    public void setNotice(String notice) { this.notice = notice; }

    public String getSitename_zh() { return sitename_zh; }
    public void setSitename_zh(String sitename_zh) { this.sitename_zh = sitename_zh; }

    public String getSitename_en() { return sitename_en; }
    public void setSitename_en(String sitename_en) { this.sitename_en = sitename_en; }
}    

