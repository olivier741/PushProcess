/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.push.model.repository;

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
public interface DualRepository extends JpaRepository<Object,Long> {
    @Modifying
    @Query("insert into Person (id,name,age) select :id,:name,:age from Dual")
    public int modifyingQueryInsertPerson(@Param("id")Long id, @Param("name")String name, @Param("age")Integer age);
}
