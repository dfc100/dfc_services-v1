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
 * The persistent class for the user_binary_info database table.
 * 
 */
@Entity
@Getter
@Setter
@Table(name="user_payment", schema = "dfc")
@NamedQuery(name="UserPayment.findAll", query="SELECT u FROM UserPayment u")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserPayment extends AuditFieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userPaymentId;

	private String level;

	private Long value;

	private String paidStatus;

	private Timestamp paidDate;

	private String screenshotPath;

	private Timestamp confirmationDate;

	private String confirmedBy;

	//bi-directional one-to-one association to User
	//@ManyToOne//(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	//@JoinColumn(name="sender_id", nullable=false, insertable=false, updatable=false)
	private Integer senderId;

	//@ManyToOne
	//@JoinColumn(name="receiver_id")
	//@ManyToOne//(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	//@JoinColumn(name="receiver_id", nullable=false, insertable=false, updatable=false)
	private Integer receiverId;

	private String transactionId;

	private Timestamp transactionDate;


}