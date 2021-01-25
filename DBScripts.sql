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
    invitation_id INT IDENTITY(1,1) PRIMARY KEY,
	subject VARCHAR (1000) NOT NULL,
	message TEXT
);

CREATE TABLE invitee(
    invitee_id INT IDENTITY(1,1) PRIMARY KEY ,
    invitee_name VARCHAR (100),
	email VARCHAR (100) NOT NULL
);

CREATE TABLE all_invitations(
    invitation_id INT FOREIGN KEY REFERENCES invitations(invitation_id),
	invitee_id INT FOREIGN KEY REFERENCES invitee(invitee_id),
	status VARCHAR (20) NOT NULL,
	status_msg VARCHAR (1000),
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

CREATE TABLE component (
    component_id INT IDENTITY(1,1) PRIMARY KEY ,
    name VARCHAR (50)
);

CREATE TABLE permission (
    permission_id INT IDENTITY(1,1) PRIMARY KEY,
	role_id INT FOREIGN KEY REFERENCES roles(role_id),
	component_id INT FOREIGN KEY REFERENCES component(component_id),
    is_create BIT NOT NULL,
	is_edit BIT NOT NULL,
	is_view BIT NOT NULL,
	is_delete BIT NOT NULL,
	CONSTRAINT uq_permission_role_component UNIQUE (role_id, component_id)
);

INSERT INTO users(user_name,password,email,contact_number,is_active)
VALUES('NotificationBoard', 'NB@2020', 'notificationboard1@gmail.com','9874563210', 1 );

INSERT INTO roles (role_name, created_by, created_date, is_active) values ('Admin',1,GETDATE(),1);
INSERT INTO roles (role_name, created_by, created_date, is_active) values ('Board Owner',1,GETDATE(),1);
INSERT INTO roles (role_name, created_by, created_date, is_active) values ('Member',1,GETDATE(),1);

ALTER table notifications ADD NTYPE VARCHAR (10) NOT NULL;
ALTER table notifications ADD DESCRIPTION TEXT NOT NULL;

CREATE TABLE NOTIFICATION_MESSAGE(
    MESSAGE_ID INT IDENTITY(1,1) PRIMARY KEY ,
	MESSAGE TEXT,
	created_by INT FOREIGN KEY REFERENCES users(user_id),
    created_date DATETIME
);

alter table notifications add updated_by INT FOREIGN KEY REFERENCES users(user_id), updated_date DATETIME;
alter table notifications drop COLUMN MESSAGE;

CREATE TABLE APP_PROPERTIES(
	PROP_NAME VARCHAR (128) NOT NULL,
	PROP_VALUE VARCHAR (1024) NOT NULL,
	CONSTRAINT UQ_APP_PROPERTIES UNIQUE(PROP_NAME, PROP_VALUE)
);

insert into APP_PROPERTIES values ('INVITE.EMAIL.BODY','Hi <<RNAME>>,

You are invited to Borad <<BNAME>>. Click on below link to access.

<<BOARD_LINK>>

Thanks
Notification Borad');
insert into APP_PROPERTIES values ('INVITE.EMAIL.SUBJECT','Borad Invitation');
insert into APP_PROPERTIES values ('BOARD_URL','https://nborad.azurewebsites.net/#/notification/getNotifications/');

insert into APP_PROPERTIES values ('USER.REGI.EMAIL.BODY','Hi,

New Board member, <<USER_NAME>> is scuccessfully reigistered to Board <<BNAME>>. 
Click on below link to approve.

<<USER_APPR_LINK>>

Thanks
Notification Borad');

insert into APP_PROPERTIES values ('USER.REGI.EMAIL.BODY','Hi <<USER_NAME>>,

Welcome to Notification Board. Your new account successfully registered. 
Click on below link to activate.

<<USER_APPR_LINK>>
');

insert into APP_PROPERTIES values ('BOARD.USER.REGI.EMAIL.SUBJECT','New Member Registration');

insert into APP_PROPERTIES values ('BOARD.USER.REGI.EMAIL.BODY','Hi,

New Board member, <<USER_NAME>> is scuccessfully reigistered to Board <<BNAME>>. 

Thanks
Notification Borad');



insert into APP_PROPERTIES values ('USER.REGI.EMAIL.SUBJECT','New Board Member Regisration');
insert into APP_PROPERTIES values ('USER.REGI.APPR.LINK','https://notification-demo-app.azurewebsites.net/user/activate?key=');

insert into APP_PROPERTIES values ('ADMIN.USER.EMAIL.ID','notificationboard1@gmail.com');

alter table users drop COLUMN created_by,updated_by;

insert into component(name) VALUES ('BOARD');
insert into component(name) VALUES ('INVITATION');
insert into component(name) VALUES ('INVITATION_LIST');
insert into component(name) VALUES ('INVITATION_APPROVALS');
insert into component(name) VALUES ('NOTIFICATIONS');

INSERT into permission (role_id, component_id, is_create, is_edit, is_view, is_delete) VALUES (1,5,1,1,1,1);
INSERT into permission (role_id, component_id, is_create, is_edit, is_view, is_delete) VALUES (1,6,1,1,1,1);
INSERT into permission (role_id, component_id, is_create, is_edit, is_view, is_delete) VALUES (1,7,1,1,1,1);
INSERT into permission (role_id, component_id, is_create, is_edit, is_view, is_delete) VALUES (1,8,1,1,1,1);
INSERT into permission (role_id, component_id, is_create, is_edit, is_view, is_delete) VALUES (1,9,1,1,1,1);

INSERT into permission (role_id, component_id, is_create, is_edit, is_view, is_delete) VALUES (4,5,1,1,1,1);
INSERT into permission (role_id, component_id, is_create, is_edit, is_view, is_delete) VALUES (4,6,1,1,1,1);
INSERT into permission (role_id, component_id, is_create, is_edit, is_view, is_delete) VALUES (4,7,1,1,1,1);
INSERT into permission (role_id, component_id, is_create, is_edit, is_view, is_delete) VALUES (4,8,1,1,1,1);
INSERT into permission (role_id, component_id, is_create, is_edit, is_view, is_delete) VALUES (4,9,1,1,1,1);

INSERT into permission (role_id, component_id, is_create, is_edit, is_view, is_delete) VALUES (5,9,0,0,1,0);

alter table all_invitations add group_id INT FOREIGN KEY REFERENCES groups(group_id) ;

alter table all_files drop COLUMN name;

update APP_PROPERTIES set PROP_VALUE = 'New Board Member Registration' where PROP_NAME = 'USER.REGI.EMAIL.SUBJECT';
update APP_PROPERTIES set PROP_VALUE = '<p>Hi <<USER_NAME>>, </p><p>Welcome to Notification Board. Your new account successfully registered. Click on below link to activate.</p><p><u><<USER_APPR_LINK>></u></p>' where PROP_NAME = 'USER.REGI.EMAIL.BODY';
update APP_PROPERTIES set PROP_VALUE = '<p>Hi <<RNAME>>, </p><p>You are invited to Borad <<BNAME>>. Click on below link to access.</p><p><u><<BOARD_LINK>></u></p>' where PROP_NAME = 'INVITE.EMAIL.BODY';
update APP_PROPERTIES set PROP_VALUE = '<p>Hi, </p><p>You are invited to Borad <<BNAME>>. Click on below link to access.</p><p><u><<BOARD_LINK>></u></p>' where PROP_NAME = 'BOARD.USER.REGI.EMAIL.BODY';

INSERT INTO APP_PROPERTIES values ('USER.APPR.SUCC.EMAIL.BODY', '<p>Hi <<USER_NAME>>, </p><p> Approved. Now you can access board <<BNAME>>.');
INSERT INTO APP_PROPERTIES values ('USER.APPR.SUCC.EMAIL.SUBJECT', 'Board Approval Confirmation');

alter table users add is_temp_pwd BIT DEFAULT 0;

INSERT into APP_PROPERTIES VALUES('RESET.PWD.EMAIL.SUBJECT', 'Forgot Password');
INSERT into APP_PROPERTIES VALUES('RESET.PWD.EMAIL.BODY', '<p>Hi <<USER_NAME>>, </p><p> You recently requested to reset password.</p><p>Your temporay password to login is : <<NEW_PWD>></p>');

INSERT INTO roles (role_name, created_by, created_date, is_active) values ('Product Owner',1,GETDATE(),1);

alter table users add is_approved BIT DEFAULT 0;
alter table groups add is_approved BIT DEFAULT 0;

INSERT INTO users(user_name,password,email,contact_number,is_active, role_id, is_approved)
VALUES('Product Owner', 'TkJAMjAyMA==', 'notificationboard31@gmail.com','9874563210', 1, (select role_id from roles where role_name='Product Owner'), 1 );

INSERT into APP_PROPERTIES VALUES('PO.APPR.EMAIL.BODY', '<p>Hi <<USER_NAME>>, </p><p> Your account is <<APPR_DESC>></p>');
INSERT into APP_PROPERTIES VALUES('PO.APPR.EMAIL.SUBJECT', 'Approval Response');

INSERT into APP_PROPERTIES VALUES('PO.GRP.APPR.EMAIL.BODY', '<p>Hi <<USER_NAME>>, </p><p> Your Board <<BNAME>> is <<APPR_DESC>></p>');
INSERT into APP_PROPERTIES VALUES('PO.GRP.APPR.EMAIL.SUBJECT', 'Board Approval Response');

INSERT into APP_PROPERTIES VALUES('PO.BO.REGI.EMAIL.BODY', '<p>Hi , </p><p>New Board Owner : <<USER_NAME>> is registered</p>');
INSERT into APP_PROPERTIES VALUES('PO.BO.REGI..EMAIL.SUBJECT', 'New User Registration');

insert into component (name) values ('ALL_USERS');
insert into component (name) values ('ALL_BOARD_OWNERS');
insert into component (name) values ('ALL_BOARDS');

insert into permission (role_id, component_id, is_create, is_edit,is_view, is_delete) values (6, 10, 0, 1, 1, 0);
insert into permission (role_id, component_id, is_create, is_edit,is_view, is_delete) values (6, 11, 0, 1, 1, 0);
insert into permission (role_id, component_id, is_create, is_edit,is_view, is_delete) values (6, 12, 0, 1, 1, 0);