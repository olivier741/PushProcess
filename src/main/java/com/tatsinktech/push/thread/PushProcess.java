/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.push.thread;

import com.tatsinktech.push.config.Load_Configuration;
import com.tatsinktech.push.kafka.service.MyKafkaProducer;
import com.tatsinktech.push.model.pushsms.Mo_Push;
import com.tatsinktech.push.model.repository.Mo_PushRepository;
import com.tatsinktech.push.util.Utils;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.annotation.PostConstruct;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 *
 * @author olivier
 */
@Component
public class PushProcess implements Runnable {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static int sleep_duration;
    private static int number_thread;
    private static int maxRow;
    private static InetAddress address;

    private int threadID;

    @Autowired
    private Load_Configuration commonConfig;

    @Autowired
    private Mo_PushRepository moPushRepo;

    @Autowired
    private MyKafkaProducer myKafkaProducer;

    @PostConstruct
    private void init() {
        PushProcess.sleep_duration = Integer.parseInt(commonConfig.getApplicationPushSleepDuration());
        PushProcess.number_thread = Integer.parseInt(commonConfig.getApplicationPushNumberThread());
        PushProcess.maxRow = Integer.parseInt(commonConfig.getApplicationPushMaxMoRow());
        PushProcess.address = Utils.gethostName();
    }

    public int getThreadID() {
        return threadID;
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    @Override
    public void run() {

        logger.info("################################## START PROCESS EXTEND ###########################");
        while (true) {
            // Removing an element from the Queue using poll()
            // The poll() method returns null if the Queue is empty.

            PageRequest pageable = PageRequest.of(0, maxRow, Sort.Direction.ASC, "id");
            int passage = 0;
            logger.info("################ START PROCESS MO BY " + Thread.currentThread().getName() + "###############");
            if (number_thread < 2) {

                List<Mo_Push> listMo_Push = moPushRepo.findMoPush(pageable);

                if (listMo_Push != null && !listMo_Push.isEmpty()) {
                    passage = 1;
                    processPush(listMo_Push);

                } else {
                    logger.info("---------> MO_PUSH TABLE IS EMPTY");
                }

            } else {
                List<Mo_Push> listMo_Push = moPushRepo.findMoPush_Thread(number_thread, threadID, pageable);
                if (listMo_Push != null && !listMo_Push.isEmpty()) {
                    passage = 1;
                    processPush(listMo_Push);

                } else {
                    logger.info("---------> MO_PUSH TABLE IS EMPTY");
                }
            }

            try {
                Thread.sleep(sleep_duration);
            } catch (Exception e) {
            }

        }
    }

    private void processPush(List<Mo_Push> listMoPush) {
        JSONObject jsonObject = new JSONObject();

        logger.info("#################### PROCESS MO_PUSH Size = " + listMoPush.size() + " ##################");
        for (Mo_Push mo : listMoPush) {

            String msisdn = mo.getMsisdn();
            String transactionid = mo.getTransactionId();
            String channel = mo.getSendChannel();
            String pushName = mo.getPushName();
            String productCode = mo.getProductCode();
            String message = mo.getMessage();
            String operator = mo.getOperator();
            String serviceName = mo.getServiceName();

            Timestamp receive_time = new Timestamp((new Date()).getTime());

            try {
                jsonObject.put("transaction_id", transactionid);
                jsonObject.put("service_id", serviceName);
                jsonObject.put("message_id", "message_1");
                jsonObject.put("sender", channel);
                jsonObject.put("receiver", msisdn);
                jsonObject.put("content", message);
                jsonObject.put("exchange_mode", "PUSH");
            } catch (JSONException e) {
                logger.error(e.getMessage());
            }
            logger.info("Msisdn :" + msisdn + " --> Channel : " + channel + " --> Message :  " + message);

            myKafkaProducer.sendDataToKafka(jsonObject.toString());
        }
    }

    public static void executeRunnables(final ExecutorService service, List<Runnable> runnables) {
        //On exécute chaque "Runnable" de la liste "runnables"
        for (Runnable r : runnables) {

            service.execute(r);
        }
        service.shutdown();
    }
}
