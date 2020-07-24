/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.push.model.repository;

import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author olivier.tatsinkou
 */
@Repository
public interface DualRepository extends JpaRepository<Object, Long> {

    @Modifying
    @Query(" INSERT INTO Mo_Push (channel,msisdn,pushName,message,operator,service_name) "
            + " SELECT  srv.sendChannel channel "
            + "         reg.msisdn msisdn "
            + "         push.pushName "
            + "         push.message "
            + "         srv.operator "
            + "         srv.serviceName "
            + " FROM Register reg "
            + " LEFT JOIN Product prod ON reg.product.id = prod.id "
            + " LEFT JOIN ServiceProvider srv ON prod.service.id = srv.id "
            + " LEFT JOIN PushSMS push ON srv.id = push.service.id "
            + " WHERE reg.status =1 "
            + " AND push.pushLevel = 'CONFIRM'"
            + " AND push.startTime <= :current_time "
            + " AND NOTEXIST (SELECT 1 FROM Push_Hist phist "
            + "                WHERE phist.msisdn = reg.msisdn "
            + "                 AND  phist.transactionId = reg.transactionId "
            + "                 AND  phist.channel = srv.sendChannel "
            + "                 AND  phist.serviceName = srv.serviceName"
            + "                 AND  phist.status = 0 ) ")
    public int loadMoPush(Date current_time);
}
