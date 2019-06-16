package com.dfc.network.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the user_tube database table.
 * 
 */
@Entity
@Getter
@Setter
@Table(name="user_tube", schema = "dfc")
@NamedQuery(name="UserTube.findAll", query="SELECT u FROM UserTube u")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserTube extends AuditFieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userTubeId;

	private String status;

	private Timestamp inProgressDate;

	private Timestamp completedDate;

	//bi-directional one-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name="user_payment_id")
	private UserPayment userPaymentId;

	private Integer starId;



}