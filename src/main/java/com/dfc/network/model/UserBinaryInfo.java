package com.dfc.network.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the user_binary_info database table.
 * 
 */
@Entity
@Getter
@Setter
@Table(name="user_binary_info", schema = "dfc")
@NamedQuery(name="UserBinaryInfo.findAll", query="SELECT u FROM UserBinaryInfo u")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserBinaryInfo extends AuditFieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userBinaryInfoId;

	private Integer parentId;

	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;

	private String position;

}