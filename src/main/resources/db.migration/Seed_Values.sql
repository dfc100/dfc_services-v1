-- -------------------------------------------
-- Table dfc.star
-- -------------------------------------------

INSERT INTO dfc.star (star_id, star_name, personal_preference, eligibility_star, no_of_legs, loan_amount, currency, ewi_amount, ewi_no_of_weeks, matching_amount, bonus_amount, weekly_capping)
VALUES(1, 'STAR', 3, 0, 0, 200, '$', 20, 10, 100, 10, 200);
INSERT INTO dfc.star (star_id, star_name, personal_preference, eligibility_star, no_of_legs, loan_amount, currency, ewi_amount, ewi_no_of_weeks, matching_amount, bonus_amount, weekly_capping)
VALUES(2, '2 STAR', 4, 1, 2, 500, '$', 20, 25, 200, 20, 500);
INSERT INTO dfc.star (star_id, star_name, personal_preference, eligibility_star, no_of_legs, loan_amount, currency, ewi_amount, ewi_no_of_weeks, matching_amount, bonus_amount, weekly_capping)
VALUES(3, '3 STAR', 6, 2, 3, 1000, '$', 40, 25, 500, 50, 1000);
INSERT INTO dfc.star (star_id, star_name, personal_preference, eligibility_star, no_of_legs, loan_amount, currency, ewi_amount, ewi_no_of_weeks, matching_amount, bonus_amount, weekly_capping)
VALUES(4, '4 STAR', 9, 3, 4, 3000, '$', 60, 50, 1000, 100, 5000);
INSERT INTO dfc.star (star_id, star_name, personal_preference, eligibility_star, no_of_legs, loan_amount, currency, ewi_amount, ewi_no_of_weeks, matching_amount, bonus_amount, weekly_capping)
VALUES(5, '5 STAR', 18, 4, 5, 10000, '$', 100, 100, 5000, 500, 10000);
INSERT INTO dfc.star (star_id, star_name, personal_preference, eligibility_star, no_of_legs, loan_amount, currency, ewi_amount, ewi_no_of_weeks, matching_amount, bonus_amount, weekly_capping)
VALUES(6, '6 STAR', 36, 5, 6, 50000, '$', 250, 200, 10000, 1000, 50000);
INSERT INTO dfc.star (star_id, star_name, personal_preference, eligibility_star, no_of_legs, loan_amount, currency, ewi_amount, ewi_no_of_weeks, matching_amount, bonus_amount, weekly_capping)
VALUES(7, '7 STAR', 72, 6, 7, 200000, '$', 500, 400, 50000, 5000, 200000);




-- -------------------------------------------
-- Table dfc.payment_master
-- -------------------------------------------
INSERT INTO dfc.payment_master ("level", value, created_dt) VALUES('1', 20, now());
INSERT INTO dfc.payment_master ("level", value, created_dt) VALUES('3', 10, now());
INSERT INTO dfc.payment_master ("level", value, created_dt) VALUES('5', 10, now());
INSERT INTO dfc.payment_master ("level", value, created_dt) VALUES('7', 10, now());
INSERT INTO dfc.payment_master ("level", value, created_dt) VALUES('Admin', 10, now());



