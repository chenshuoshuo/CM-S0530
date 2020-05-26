drop table if exists ccr_user;
create table ccr_user (
	user_id                 SERIAL          not null,
	open_id                 VARCHAR(255)    null,
	pass_word               VARCHAR(255)    null,
	user_code               VARCHAR(255)    null,
	cas_ticket              VARCHAR(255)    null,
	user_group              VARCHAR(255)    null,
	update_time             TIMESTAMP       null,
	is_admin                BOOL            null,
	head_path               VARCHAR(255)    null,
	head_url                VARCHAR(255)    null,
	user_name               VARCHAR(64)     null,
	user_auth               VARCHAR(64)     null,
	constraint PK_USER_USER_ID primary key (user_id)
);
ALTER TABLE "public"."ccr_user" ADD CONSTRAINT "cas_ticket_index" UNIQUE ("cas_ticket");
ALTER TABLE "public"."ccr_user" ADD CONSTRAINT "open_id_index" UNIQUE ("open_id");
ALTER TABLE "public"."ccr_user" ADD CONSTRAINT "user_code_index" UNIQUE ("user_code");
