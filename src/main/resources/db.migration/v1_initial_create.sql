-- -------------------------------------------
-- Table dfc
-- -------------------------------------------
create schema  dfc;

-- -------------------------------------------
-- Table dfc.star
-- -------------------------------------------
create table  dfc.star (
	star_id serial not null,
	star_name VARCHAR(20) not null,
	personal_preference SMALLINT not null,
	eligibility_star SMALLINT not null,
	no_of_legs SMALLINT not null,
	loan_amount BIGINT not null,
	currency VARCHAR(25) not null,
	ewi_amount SMALLINT not null,
	ewi_no_of_weeks SMALLINT not null,
	matching_amount BIGINT not null,
	bonus_amount BIGINT not null,
	weekly_capping BIGINT not null,
	created_dt TIMESTAMP(0) null default now(),
	updated_dt TIMESTAMP(0) null,
    primary key (star_id)

);


-- -------------------------------------------
-- Table dfc.payment_master
-- -------------------------------------------
create table  dfc.payment_master (
	payment_master_id serial not null,
	level VARCHAR(20) not null,
	value SMALLINT not null,
	created_dt TIMESTAMP(0) null default now(),
	updated_dt TIMESTAMP(0) null,
    primary key (payment_master_id)

);

-- -------------------------------------------
-- Table dfc.user
-- -------------------------------------------
create table  dfc.user (
	user_id serial not null,
	user_name VARCHAR(20) not null,
	password text not null,
    star_id BIGINT,
	full_name VARCHAR(255) not null,
	gender VARCHAR(15) not null,
	state VARCHAR(30) null,
	mobile_no VARCHAR(15) not null,
	email text not null,
	date_of_birth VARCHAR(20) not null,
	city VARCHAR(20) null,
	country VARCHAR(50) null,
	enabled BOOL null,
	status VARCHAR(20) not null,
	email_verified BOOL null,
	referral_user_id BIGINT not null,
	eos_fin_id text null,
	referral_binary_position CHAR not null,
	created_dt TIMESTAMP(0) null default now(),
    updated_dt TIMESTAMP(0) null,
	primary key (user_id),
	CONSTRAINT fk_user_star
        	  FOREIGN KEY (star_id)
        	  REFERENCES dfc.star (star_id)
        	  ON DELETE CASCADE
        	  ON UPDATE NO ACTION
);

CREATE INDEX fk_user_star_idx ON dfc.star (star_id ASC);


-- -------------------------------------------
-- Table dfc.user_sunflower_info
-- -------------------------------------------
create table  dfc.user_sunflower_info (
	user_sunflower_info_id serial not null,
	user_id BIGINT not null,
    referral_user_id BIGINT not null,
    created_dt TIMESTAMP(0) null default now(),
    updated_dt TIMESTAMP(0) null,
    primary key (user_sunflower_info_id)
	,CONSTRAINT fk_user_sunflower_info_user
         	  FOREIGN KEY (user_id)
         	  REFERENCES dfc.user (user_id)
         	  ON DELETE CASCADE
         	  ON UPDATE NO ACTION
         );

    CREATE INDEX fk_user_sunflower_info_user_idx ON dfc.user_sunflower_info (user_id ASC);


-- -------------------------------------------
-- Table dfc.user_binary_info
-- -------------------------------------------
create table  dfc.user_binary_info (
	user_binary_info_id serial not null,
	user_id BIGINT not null,
    parent_id BIGINT not null,
    position CHAR(1) not null,
    created_dt TIMESTAMP(0) null default now(),
    updated_dt TIMESTAMP(0) null,
    primary key (user_binary_info_id)
    ,CONSTRAINT fk_user_binary_info_user
         	  FOREIGN KEY (user_id)
         	  REFERENCES dfc.user (user_id)
         	  ON DELETE CASCADE
         	  ON UPDATE NO ACTION
         );

    CREATE INDEX fk_user_binary_info_user_idx ON dfc.user_binary_info (user_id ASC);


-- -------------------------------------------
-- Table dfc.user_payment
-- -------------------------------------------
create table  dfc.user_payment (
	user_payment_id serial not null,
	sender_id BIGINT not null,
    receiver_id BIGINT not null,
    level VARCHAR(10) not null,
    value numeric not null,
    paid_status VARCHAR(20)  null,
    paid_date TIMESTAMP(0) null,
    screenshot_path text null,
    confirmation_status VARCHAR(20) null,
    confirmation_date TIMESTAMP(0) null,
    confirmed_by text null,
    created_dt TIMESTAMP(0) null default now(),
    updated_dt TIMESTAMP(0) null,
    primary key (user_payment_id)
	,CONSTRAINT fk_user_payment_sender
         	  FOREIGN KEY (sender_id)
         	  REFERENCES dfc.user (user_id)
         	  ON DELETE CASCADE
         	  ON UPDATE NO ACTION
     ,CONSTRAINT fk_user_payment_receiver
               	  FOREIGN KEY (receiver_id)
               	  REFERENCES dfc.user (user_id)
               	  ON DELETE CASCADE
               	  ON UPDATE NO ACTION

         );

    CREATE INDEX fk_user_payment_sender_idx ON dfc.user_payment (sender_id ASC);
    CREATE INDEX fk_user_payment_receiver_idx ON dfc.user_payment (receiver_id ASC);


-- -------------------------------------------
-- Table dfc.verification_token
-- -------------------------------------------
create table  dfc.verification_token (
	verification_token_id serial not null,
	token text not null,
	user_id BIGINT not null,
	expiry_date TIMESTAMP(0) null,
    primary key (verification_token_id)
    ,CONSTRAINT fk_verification_token_user
                 	  FOREIGN KEY (user_id)
                 	  REFERENCES dfc.user (user_id)
                 	  ON DELETE CASCADE
                 	  ON UPDATE NO ACTION

);


-- -------------------------------------------
-- Table dfc.password_reset_token
-- -------------------------------------------
create table  dfc.password_reset_token (
	password_reset_token_id serial not null,
	token text not null,
	user_id BIGINT not null,
	expiry_date TIMESTAMP(0) null,
    primary key (password_reset_token_id)
    ,CONSTRAINT fk_password_reset_token_user
                 	  FOREIGN KEY (user_id)
                 	  REFERENCES dfc.user (user_id)
                 	  ON DELETE CASCADE
                 	  ON UPDATE NO ACTION

);


