package org.seasar.chronos.sastruts.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import java.sql.Timestamp;


@Entity
public class S2session {

    @Id
    @GeneratedValue
    @Column(name="SESSION_ID")
    public String sessionId;

    @Id
    @GeneratedValue
    public String name;

    public byte[] value;

    @Temporal(TemporalType.DATE)
    @Column(name="LAST_ACCESS")
    public Timestamp lastAccess;

}