

CREATE DATABASE stingraydb;

\c stingraydb

CREATE TABLE stingray_users (
	    user_email text NOT NULL,
	    password text NOT NULL
);

CREATE UNIQUE INDEX stingray_users_index ON stingray_users (user_email);

CREATE TABLE stingray_tenants (tenant_name text NOT NULL,
	active_flag SMALLINT NOT NULL, 
	create_time TIMESTAMPTZ NOT NULL DEFAULT NOW() );

CREATE UNIQUE INDEX stingray_tenants_index on stingray_tenants (tenant_name);

CREATE TABLE stingray_tenant_users (tenant_name text NOT NULL, user_email text NOT NULL, tenant_role text NOT NULL);

CREATE UNIQUE INDEX stingray_tenant_users_index on stingray_tenant_users (tenant_name, user_email);

CREATE user stingray_user with password 'stingraypw';

GRANT all on stingray_users to stingray_user;
GRANT all on stingray_tenants to stingray_user;
GRANT all on stingray_tenant_users to stingray_user;

INSERT into stingray_users (user_email, password) 
	VALUES ('master@stingraydb.io', 'stingraypw');

INSERT into stingray_tenants (tenant_name, active_flag) VALUES ('stingraydb.io', 0);

INSERT into stingray_tenant_users (tenant_name, user_email, tenant_role)
	VALUES ('stingraydb.io', 'master@stingraydb.io', 'ADMIN'); 

CREATE TABLE stingray_message (tenant_name text NOT NULL,
	user_email text NOT NULL,
	device_name text NOT NULL,
	message_text text NOT NULL,
	create_time TIMESTAMPTZ NOT NULL DEFAULT NOW() );

GRANT all on stingray_message to stingray_user;

