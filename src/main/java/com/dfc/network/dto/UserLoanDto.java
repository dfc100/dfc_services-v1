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
public class UserLoanDto {

	private Integer userId;

	private Integer userLoanId;

	private Integer loanAmount;

	private String currency;

	private Integer ewiAmount;

	private Integer ewiNoOfWeeks;

	private String loanStatus;

	private String loanScreenshotPath;

	private Timestamp loanConfirmedDate;

	private Timestamp loanRequestedDate;

	private Integer starId;

	private String userName;

	private String fullName;

	private String eosFinId;

	private String transactionId;

	private Timestamp transactionDate;

}