package com;

import com.Service.SiteService;
import com.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SystemSchedule {

    @Autowired
    private SiteService siteService;
    private UserService userService;
    
    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    @Scheduled(cron = "*/1 * * * * ?")
    public void execute() throws Exception{
        /*siteService.refreshsite();
        try {
            Thread.sleep(60000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
    
}
