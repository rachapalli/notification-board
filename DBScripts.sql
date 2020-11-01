CREATE TABLE users(
    user_id INT IDENTITY(1,1) PRIMARY KEY ,
    user_name VARCHAR (100) NOT NULL,
    password VARCHAR (100) NOT NULL,
	email VARCHAR (100) NOT NULL,
	alternate_email VARCHAR (100), 
	contact_number VARCHAR (15) NOT NULL,
	created_by INT,
    created_date DATETIME,
	updated_by INT,
    updated_date DATETIME,
	is_active BIT DEFAULT 1,
	CONSTRAINT UQ_USERS_EMAIL UNIQUE(email)
);

CREATE TABLE roles(
    role_id INT IDENTITY(1,1) PRIMARY KEY ,
    role_name VARCHAR (100) NOT NULL,
	created_by INT,
    created_date DATETIME,
	updated_by INT,
    updated_date DATETIME,
	is_active BIT DEFAULT 1,
	CONSTRAINT UQ_ROLES_ROLE_NAME UNIQUE(role_name)
);

CREATE TABLE user_roles(
    user_id INT FOREIGN KEY REFERENCES users(user_id),
	role_id INT FOREIGN KEY REFERENCES roles(role_id),
	created_by INT FOREIGN KEY REFERENCES users(user_id),
    created_date DATETIME,
	is_active BIT DEFAULT 1,
	CONSTRAINT uq_user_role UNIQUE (user_id, role_id)
);

CREATE TABLE groups(
    group_id INT IDENTITY(1,1) PRIMARY KEY ,
	group_name VARCHAR (100) NOT NULL,
	is_public BIT,
	created_by INT FOREIGN KEY REFERENCES users(user_id),
    created_date DATETIME,
	is_active BIT DEFAULT 1,
	CONSTRAINT UQ_GROUPS_GROUP_NAME UNIQUE(group_name)
);

CREATE TABLE user_groups(
    user_id INT FOREIGN KEY REFERENCES users(user_id),
	group_id INT FOREIGN KEY REFERENCES groups(group_id),
	created_by INT FOREIGN KEY REFERENCES users(user_id),
    created_date DATETIME,
	is_active BIT DEFAULT 1,
	CONSTRAINT uq_user_group UNIQUE (user_id, group_id)
);

CREATE TABLE invitations(
    invitation_id INT IDENTITY(1,1) PRIMARY KEY ,
	message TEXT,
	created_by INT FOREIGN KEY REFERENCES users(user_id),
    created_date DATETIME
);

CREATE TABLE invitee(
    invitee_id INT IDENTITY(1,1) PRIMARY KEY ,
    invitee_name VARCHAR (100) NOT NULL,
	email VARCHAR (100) NOT NULL,
	contact_number VARCHAR (15) NOT NULL,
	created_by INT FOREIGN KEY REFERENCES users(user_id),
    created_date DATETIME
);

CREATE TABLE all_invitations(
    invitation_id INT FOREIGN KEY REFERENCES invitations(invitation_id),
	invitee_id INT FOREIGN KEY REFERENCES invitee(invitee_id),
	status VARCHAR (20) NOT NULL,
	created_by INT FOREIGN KEY REFERENCES users(user_id),
    created_date DATETIME,
	CONSTRAINT uq_invitaion_invitee UNIQUE (invitation_id, invitee_id)
);

CREATE TABLE all_files(
    file_id INT IDENTITY(1,1) PRIMARY KEY ,
    name VARCHAR (100) NOT NULL,
	file_key VARCHAR (1000) NOT NULL,
	created_by INT FOREIGN KEY REFERENCES users(user_id),
    created_date DATETIME
);

CREATE TABLE notifications(
    notification_id INT IDENTITY(1,1) PRIMARY KEY ,
	message TEXT,
	notification_type VARCHAR (10) NOT NULL,
	file_id INT,
	created_by INT FOREIGN KEY REFERENCES users(user_id),
    created_date DATETIME
);

CREATE TABLE group_notifications(
    group_id INT FOREIGN KEY REFERENCES groups(group_id),
	notification_id INT FOREIGN KEY REFERENCES notifications(notification_id),
	created_by INT FOREIGN KEY REFERENCES users(user_id),
    created_date DATETIME,
	is_active BIT DEFAULT 1,
	CONSTRAINT uq_group_notifications UNIQUE (group_id, notification_id)
);

INSERT INTO users(user_name,password,email,contact_number,is_active)
VALUES('NotificationBoard', 'NB@2020', 'notificationboard1@gmail.com','9874563210', 1 );

INSERT INTO roles (role_name, created_by, created_date, is_active) values ('Admin',1,GETDATE(),1);
INSERT INTO roles (role_name, created_by, created_date, is_active) values ('Board Owner',1,GETDATE(),1);
INSERT INTO roles (role_name, created_by, created_date, is_active) values ('Member',1,GETDATE(),1);