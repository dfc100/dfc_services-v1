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
 * The persistent class for the user_loan_repayment database table.
 * 
 */
@Entity
@Getter
@Setter
@Table(name="user_loan_repayment", schema = "dfc")
@NamedQuery(name="UserLoanRepayment.findAll", query="SELECT u FROM UserLoanRepayment u")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserLoanRepayment extends AuditFieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userLoanRepaymentId;

	private Integer ewiAmount;

	private String ewiScreenshotPath;

	private Timestamp ewiDueDate;

	private Timestamp ewiPaidDate;

	private Timestamp ewiConfirmedDate;

	private String ewiStatus;

	private String ewiAction;

	//bi-directional one-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_loan_id")
	private UserLoan userLoan;

	private String transactionId;

	private Timestamp transactionDate;

}