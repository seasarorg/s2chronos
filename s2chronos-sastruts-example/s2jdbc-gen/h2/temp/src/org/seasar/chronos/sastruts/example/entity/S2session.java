package org.seasar.chronos.sastruts.example.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * S2sessionエンティティクラスです。
 * 
 * @author S2JDBC-Gen
 */
@Entity
public class S2session {

    /** sessionIdプロパティ */
    @Id
    @Column(columnDefinition = "varchar(2147483647)")
    public String sessionId;

    /** nameプロパティ */
    @Id
    @Column(columnDefinition = "varchar(255)")
    public String name;

    /** valueプロパティ */
    @Column(columnDefinition = "varbinary(10485760)", nullable = true, unique = false)
    public byte[] value;

    /** lastAccessプロパティ */
    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "timestamp", nullable = true, unique = false)
    public Date lastAccess;
}