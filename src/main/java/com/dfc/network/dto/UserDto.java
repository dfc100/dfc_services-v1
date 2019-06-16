package com.dfc.network.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Integer userId;

    @NonNull
    private String userName;

    @NotEmpty
    @NonNull
    private String password;

    private String oldPassword;

    private Integer starId;
    @NotEmpty
    @NonNull
    private String fullName;


    @NotEmpty
    @NonNull
    private String gender;
    @NotEmpty
    @NonNull
    private String state;

    @Size(min=10,max=10)
    private String mobileNo;

    @NotEmpty
    @NonNull
    @Email
    private String email;

    @NotEmpty
    @NonNull
    private String dateOfBirth;

    private String city;

    private String country;

    private Boolean enabled;

    private Boolean emailVerified;

    @NonNull
    private Integer referralId;

    @NotEmpty
    @NonNull
    private String position;

    private String status;

    private String eosFinId;

}
