package com.dfc.network.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the user database table.
 * 
 */
@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name="star", schema = "dfc")
@NamedQuery(name="Star.findAll", query="SELECT u FROM Star u")
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)

public class Star extends AuditFieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer starId;

	private String starName;

	private Integer personalPreference;

	private Integer eligibilityStar;

	private Integer noOfLegs;

	private Integer loanAmount;

	private String currency;

	private Integer ewiAmount;

	private Integer ewiNoOfWeeks;

	private Integer matchingAmount;

	private Integer bonusAmount;

	private Integer weeklyCapping;

	//bi-directional one-to-one association to User
	@OneToOne(mappedBy="star")
	private User user;

}