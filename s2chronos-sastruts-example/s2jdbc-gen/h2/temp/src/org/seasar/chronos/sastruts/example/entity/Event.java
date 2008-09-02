package org.seasar.chronos.sastruts.example.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Eventエンティティクラスです。
 * 
 * @author S2JDBC-Gen
 */
@Entity
public class Event {

    /** eventIdプロパティ */
    @Id
    @GeneratedValue
    @Column(columnDefinition = "bigint default (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_0)")
    public Long eventId;

    /** eventDateプロパティ */
    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "timestamp", nullable = true, unique = false)
    public Date eventDate;

    /** eventTextプロパティ */
    @Column(columnDefinition = "varchar(255)", nullable = true, unique = false)
    public String eventText;
}