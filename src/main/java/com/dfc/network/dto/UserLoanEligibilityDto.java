package com.dfc.network.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoanEligibilityDto {

	private Integer starId;

	private String starName;

	private Integer personalPreference;

	private Integer eligibilityStar;

	private Integer noOfLegs;

	private Integer loanAmount;

	private String currency;

	private Integer ewiAmount;

	private Integer ewiNoOfWeeks;

	private String loanEligibilityStatus;

	private Integer userId;

}