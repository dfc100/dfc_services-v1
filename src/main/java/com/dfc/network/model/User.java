package com.dfc.network.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
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
@Table(name="user", schema = "dfc")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@EqualsAndHashCode(callSuper = false)
public class User extends AuditFieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	private String city;

	@Column(name="mobile_no")
	private String mobileNo;

	@Column(name="date_of_birth")
	private String dateOfBirth;

	private String email;

	private Boolean enabled;

	@Column(name="full_name")
	private String fullName;

	private String gender;

	private String password;

	private String state;

	private String country;

	@Column(name="user_name")
	private String userName;

	@Column(name="email_verified")
	private Boolean emailVerified;

	@NonNull
	private Integer referralUserId;

	@NonNull
	private String referralBinaryPosition;

	@NonNull
	private String status;

	private String eosFinId;

	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="star_id")
	private Star star;

	@OneToOne(mappedBy="user")
	private VerificationToken verificationToken;

	@OneToOne(mappedBy="user")
	private PasswordResetToken passwordResetToken;

	//bi-directional one-to-one association to KycDetail
	@OneToOne(mappedBy="user")
	private UserBinaryInfo userBinaryInfo;

	//bi-directional one-to-one association to KycDetail
	@OneToOne(mappedBy="user")
	private UserSunflowerInfo userSunflowerInfo;

}