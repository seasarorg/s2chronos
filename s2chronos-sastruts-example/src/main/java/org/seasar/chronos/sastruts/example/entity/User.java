package org.seasar.chronos.sastruts.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Userエンティティクラスです。
 * 
 * @author S2JDBC-Gen
 */
@Entity
public class User {

	/** userIdプロパティ */
	@Id
	@GeneratedValue
	@Column(columnDefinition = "bigint default (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_1)")
	public Long userId;

	/** userNameプロパティ */
	@Column(columnDefinition = "varchar(255)", nullable = true, unique = false)
	public String userName;

	/** lastNameプロパティ */
	@Column(columnDefinition = "varchar(255)", nullable = true, unique = false)
	public String lastName;

	/** firstNameプロパティ */
	@Column(columnDefinition = "varchar(255)", nullable = true, unique = false)
	public String firstName;

	/** emailプロパティ */
	@Column(columnDefinition = "varchar(64)", nullable = true, unique = false)
	public String email;

	/** userStatusプロパティ */
	@Column(columnDefinition = "integer", nullable = true, unique = false)
	public Integer userStatus = STATUS_ENABLE;

	public static final Integer STATUS_DISABLE = Integer.valueOf(0);
	public static final Integer STATUS_ENABLE = Integer.valueOf(1);
	public static final Integer STATUS_SUSPEND = Integer.valueOf(2);

}