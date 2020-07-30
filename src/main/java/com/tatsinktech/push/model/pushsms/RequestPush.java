///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tatsinktech.push.model.pushsms;
//
//import java.io.Serializable;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Lob;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//import org.hibernate.annotations.Immutable;
//import org.hibernate.annotations.Subselect;
//import org.hibernate.annotations.Synchronize;
//
///**
// *
// * @author olivier.tatsinkou
// */
//@Entity
//@Data
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Subselect(
//              " SELECT  srv.sendChannel channel, "
//            + "         reg.msisdn msisdn, "
//            + "         push.pushName, "
//            + "         push.message, "
//            + "         srv.operator, "
//            + "         prod.productCode, "
//            + "         srv.serviceName service_name"
//            + " FROM Register reg "
//            + " LEFT JOIN Product prod ON reg.product.id = prod.id "
//            + " LEFT JOIN ServiceProvider srv ON prod.service.id = srv.id "
//            + " LEFT JOIN PushSMS push ON srv.id = push.service.id "
//            + " WHERE reg.status =1 "
//            + " AND prod.productCode IS NOT NULL"
//            + " AND srv.serviceName IS NOT NULL "
//            + " AND push.pushName IS NOT NULL "
//            + " AND push.pushLevel = 'CONFIRM'"
//            + " AND push.startTime <= :current_time "
//            + " AND NOT EXISTS (    SELECT 1 FROM Push_Hist phist "
//            + "                     WHERE phist.msisdn = reg.msisdn "
//            + "                     AND  phist.transactionId = reg.transactionId "
//            + "                     AND  phist.channel = srv.sendChannel "
//            + "                     AND  phist.serviceName = srv.serviceName "
//            + "                     AND  phist.pushName = push.pushName "
//            + "                     AND  phist.status = 0 ) ")
//@Synchronize({"Register", "Product", "ServiceProvider", "PushSMS", "Push_Hist"})
//@Immutable
//public class RequestPush implements Serializable {
//
//    @Id
//    private long id;
//
//    @Column(name = "channel")
//    private String channel;
//
//    @Column(name = "msisdn")
//    private String msisdn;
//
//    @Column(name = "push_name")
//    private String pushName;
//
//    @Lob
//    @Column(name = "message")
//    private String message;
//
//    @Column(name = "operator")
//    private String operator;
//
//    @Column(name = "product_code")
//    private String productCode;
//
//    @Column(name = "service_name")
//    private String service_name;
//}
