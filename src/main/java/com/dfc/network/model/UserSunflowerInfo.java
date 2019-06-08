package com.dfc.network.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the user_sunflower_info database table.
 * 
 */
@Entity
@Getter
@Setter
@Table(name="user_sunflower_info", schema = "dfc")
@NamedQuery(name="UserSunflowerInfo.findAll", query="SELECT u FROM UserSunflowerInfo u")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserSunflowerInfo extends AuditFieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userSunflowerInfoId;

	//bi-directional many-to-one association to User
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;

	private Integer referralUserId;

}