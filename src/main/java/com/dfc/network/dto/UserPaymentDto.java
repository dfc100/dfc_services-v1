package com.dfc.network.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

import java.sql.Timestamp;


@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPaymentDto {

	private Integer userPaymentId;

	private String level;

	private Long value;

	private String paidStatus;

	private Timestamp paidDate;

	private String screenshotPath;

	private Timestamp confirmationDate;

	private String confirmedBy;

	private Integer senderId;

	private Integer receiverId;

	private String senderFullName;

	private String senderUserName;

	private String receiverUserName;

	private String receiverFullName;

	private String senderEosFinId;

	private String receiverEosFinId;

	private Resource resource;


}