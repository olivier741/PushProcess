/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.push.model.repository;

import com.tatsinktech.push.model.pushsms.Mo_Push;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author olivier.tatsinkou
 */
@Repository
public interface Mo_PushRepository extends JpaRepository<Mo_Push, Long> {

    @Modifying
    @Transactional
    @Query(" INSERT INTO Mo_Push (sendChannel,msisdn,transactionId,pushName,message,operator,productCode,serviceName) \n"
            + " SELECT  srv.sendChannel , \n"
            + "         reg.msisdn , \n"
            + "         reg.transactionId, \n"
            + "         push.pushName, \n"
            + "         push.message, \n"
            + "         srv.operator, \n"
            + "         prod.productCode, \n"
            + "         srv.serviceName  \n"
            + " FROM Register reg \n"
            + " LEFT JOIN Product prod ON reg.product.id = prod.id  \n"
            + " LEFT JOIN ServiceProvider srv ON prod.service.id = srv.id \n"
            + " LEFT JOIN PushSMS push ON srv.id = push.service.id \n"
            + " WHERE reg.status =1 \n"
            + " AND push.pushLevel = 'CONFIRM' \n"
            + " AND push.startTime <= :current_time \n"
            + " AND prod.productCode IS NOT NULL \n"
            + " AND srv.serviceName IS NOT NULL \n"
            + " AND push.pushName IS NOT NULL \n"
            + " AND NOT EXISTS (  SELECT 1  FROM Mo_Push mopush \n"
            + "                   WHERE mopush.msisdn = reg.msisdn \n"
            + "                   AND   mopush.transactionId = reg.transactionId \n"
            + "                   AND   mopush.pushName = push.pushName \n"
            + "                   AND   mopush.serviceName = srv.serviceName ) \n"
            + " AND NOT EXISTS (  SELECT 1 FROM Push_Hist phist \n"
            + "                   WHERE phist.msisdn = reg.msisdn \n"
            + "                   AND  phist.transactionId = reg.transactionId \n"
            + "                   AND  phist.channel = srv.sendChannel \n"
            + "                   AND  phist.serviceName = srv.serviceName \n"
            + "                   AND  phist.pushName = push.pushName \n"
            + "                   AND  phist.status = 0 ) \n")
    void loadMoPush(Date current_time);

    @Query("SELECT mo FROM Mo_Push mo WHERE MOD(mo.id, :number_thread ) = :thead_id ")
    List<Mo_Push> findMoPush_Thread(int number_thread, int thead_id, Pageable pageable);

    @Query("SELECT mo FROM Mo_Push mo ")
    List<Mo_Push> findMoPush(Pageable pageable);
}
