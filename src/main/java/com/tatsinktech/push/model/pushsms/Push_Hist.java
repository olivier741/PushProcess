/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.push.model.pushsms;

import com.tatsinktech.push.model.AbstractModel;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author olivier.tatsinkou
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString (exclude = "listProduct")
@Table(name = "push_hist")
public class Push_Hist extends AbstractModel<Long>{
    
    
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "channel")
    private String channel;
    
    @Column(name = "push_name", nullable = false)
    private String pushName;

    @Column(name = "receive_time")
    private Timestamp receiveTime;

    @UpdateTimestamp
    @Column(name = "process_time")
    private Timestamp processTime;


    @Column(name = "charge_fee")
    private long chargeFee;

    @Column(name = "charge_status")
    private int chargeStatus;

    @Column(name = "charge_error")
    private String chargeError;

    @Column(name = "charge_time")
    private Timestamp chargeTime;

    @Column(name = "duration")
    private long duration;

    @Column(name = "process_unit")
    private String processUnit;

    @Column(name = "Ip_address")
    private String IpAddress;
    
    @Column(name = "exchange_mode")
    private String exchangeMode;
    
     @Column(name = "productCode")
    private String productCode;
     
    @Column(name = "commad_name")
    private String commandName;
    
    @Column(name = "commad_code")
    private String commandCode;
    
    @Column(name = "param_name")
    private String paramName;
    
    
    @Column(name = "service_name")
    private String serviceName;
}
