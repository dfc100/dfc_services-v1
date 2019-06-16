package com.dfc.network.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the user_loan database table.
 * 
 */
@Entity
@Getter
@Setter
@Table(name="user_loan", schema = "dfc")
@NamedQuery(name="UserLoan.findAll", query="SELECT u FROM UserLoan u")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserLoan extends AuditFieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userLoanId;

	private Integer loanAmount;

	private String loanScreenshotPath;

	private Timestamp loanConfirmedDate;

	private Timestamp loanRequestedDate;

	private String loanCurrency;

	private String loanStatus;

	//bi-directional one-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	private Integer starId;

	@OneToMany(mappedBy = "userLoan", cascade = CascadeType.ALL)
	private Set<UserLoanRepayment> userLoanRepayments;

	private String transactionId;

	private Timestamp transactionDate;

}