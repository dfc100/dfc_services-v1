package com.dfc.network.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the payment_master database table.
 * 
 */
@Entity
@Getter
@Setter
@Table(name="payment_master", schema = "dfc")
@NamedQuery(name="PaymentMaster.findAll", query="SELECT u FROM PaymentMaster u")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentMaster extends AuditFieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paymentMasterId;

	private String level;

	private Long value;

}