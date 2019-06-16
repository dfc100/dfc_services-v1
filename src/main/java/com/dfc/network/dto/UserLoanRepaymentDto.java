package com.dfc.network.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoanRepaymentDto {

	private Integer userId;

	private Integer userLoanId;

	private Integer loanAmount;

	private String currency;

	private Integer ewiAmount;

	private Integer week;

	private String loanStatus;

	private String userName;

	private String fullName;

	private String eosFinId;

	private String transactionId;

	private Timestamp transactionDate;

	private Integer userLoanRepaymentId;

	private String ewiScreenshotPath;

	private Timestamp ewiDueDate;

	private Timestamp ewiPaidDate;

	private Timestamp ewiConfirmedDate;

	private String ewiStatus;

	private String ewiAction;

}