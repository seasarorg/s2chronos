package org.seasar.chronos.sastruts.example.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp", nullable = true, unique = false)
	public Timestamp eventDate;

	/** eventTextプロパティ */
	@Column(columnDefinition = "varchar(255)", nullable = true, unique = false)
	public String eventText;

	/** eventStatusプロパティ */
	@Column(columnDefinition = "integer", nullable = true, unique = false)
	public Integer eventStatus;

	/** createDateプロパティ */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp", nullable = true, unique = false)
	public Timestamp createDate = new Timestamp(System.currentTimeMillis());

	/** updateDateプロパティ */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp", nullable = true, unique = false)
	public Timestamp updateDate;

	/** versionNoプロパティ */
	@Version
	@Column(columnDefinition = "bigint", nullable = true, unique = false)
	public Long versionNo;

	public static final Integer STATUS_NONE = Integer.valueOf(0);
	public static final Integer STATUS_ING = Integer.valueOf(1);
	public static final Integer STATUS_DONE = Integer.valueOf(2);
	public static final Integer STATUS_ERR = Integer.valueOf(3);

}