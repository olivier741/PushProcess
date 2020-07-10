/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.push;

import com.tatsinktech.push.config.Load_Configuration;
import com.tatsinktech.push.thread.PushProcess;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author olivier.tatsinkou
 */
@Component
public class ApplicationLaucher implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(ApplicationLaucher.class);

    @Autowired
    private Load_Configuration commonConfig;

    @Autowired
    ConfigurableApplicationContext ConfAppContext;


    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("******************* InitializingBean *******************************");

        int pushProcess_num = Integer.parseInt(commonConfig.getApplicationPushNumberThread());
        int pushProcess_pool = Integer.parseInt(commonConfig.getApplicationPushThreadPool());

        List<Runnable> pushProcess_runnables = new ArrayList<Runnable>();

        // pushProcessThread
        for (int i = 0; i < pushProcess_num; i++) {
            PushProcess pushProcessThread = (PushProcess) ConfAppContext.getBean(PushProcess.class);
            pushProcess_runnables.add(pushProcessThread);
        }
        ExecutorService push_Execute = Executors.newFixedThreadPool(pushProcess_pool);
        PushProcess.executeRunnables(push_Execute, pushProcess_runnables);
       
    }

}
