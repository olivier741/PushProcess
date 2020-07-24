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
@ToString
@Table(name = "push_hist")
public class Push_Hist extends AbstractModel<Long> {

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "msisdn", nullable = false)
    private String msisdn;

    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "push_name", nullable = false)
    private String pushName;
    
    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "operator")
    private String operator;



    @UpdateTimestamp
    @Column(name = "process_time")
    private Timestamp processTime;

    @Column(name = "status")
    private int status;

    @Column(name = "process_unit")
    private String processUnit;

    @Column(name = "Ip_address")
    private String IpAddress;

    @Column(name = "service_name", nullable = false)
    private String serviceName;
}
