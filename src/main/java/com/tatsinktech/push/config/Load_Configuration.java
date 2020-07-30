/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.push.config;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author olivier
 */
@Component
public class Load_Configuration implements Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Value("${application.push.numberThread}")
    private String applicationPushNumberThread;

    @Value("${application.push.threadPool}")
    private String applicationPushThreadPool;

    @Value("${application.push.sleep-duration}")
    private String applicationPushSleepDuration;

    @Value("${application.push.mo-maxRow}")
    private String applicationPushMaxMoRow;

    @Value("${application.push.scheduler-fixedDelay}")
    private Integer applicationPushFixeDelay;

    @Value("${application.push.scheduler-poolSize}")
    private Integer applicationPushPoolSize;

    @Value("${spring.kafka.producer.topic}")
    private String producer_topic;

    @Value("${spring.kafka.zookeeper.host}")
    private String zookeeperHosts;

    @Value("${spring.kafka.topic.partitions}")
    private String partitions;

    @Value("${spring.kafka.topic.replication}")
    private String replicationFactor;

    @Value("${spring.kafka.topic.session-timeOut-in-ms}")
    private String sessionTimeOutInMs;

    @Value("${spring.kafka.topic.connection-timeOut-in-ms}")
    private String connectionTimeOutInMs;

    public String getApplicationPushNumberThread() {
        return applicationPushNumberThread;
    }

    public String getApplicationPushThreadPool() {
        return applicationPushThreadPool;
    }

    public String getApplicationPushSleepDuration() {
        return applicationPushSleepDuration;
    }

    public String getProducer_topic() {
        return producer_topic;
    }

    public String getZookeeperHosts() {
        return zookeeperHosts;
    }

    public String getPartitions() {
        return partitions;
    }

    public String getReplicationFactor() {
        return replicationFactor;
    }

    public String getSessionTimeOutInMs() {
        return sessionTimeOutInMs;
    }

    public String getConnectionTimeOutInMs() {
        return connectionTimeOutInMs;
    }

    public Integer getApplicationPushFixeDelay() {
        return applicationPushFixeDelay;
    }

    public Integer getApplicationPushPoolSize() {
        return applicationPushPoolSize;
    }

    public String getApplicationPushMaxMoRow() {
        return applicationPushMaxMoRow;
    }

    
    
}
