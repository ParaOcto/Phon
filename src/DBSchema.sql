drop database Phon;

create database Phon;
go
use Phon;

create table users(
	userId int identity(1, 1) primary key,
	username varchar(50) not null,
	password_hash varchar(50) not null,
	roleUser varchar(15) check(roleUser in ('Phon', 'Anonymous'))
)

create table post(
	postId int identity(1, 1) primary key,
	userId int ,
	content text,
	createdAt date,
	imageUrl varchar(200),
	foreign key (userId) references users(userId)
)

create table comments(
	commentId int identity(1, 1) primary key,
	postId int ,
	userId int ,
	content text,
	createdAt date,
	foreign key (postId) references post(postId),
	foreign key (userId) references users(userId)
)

CREATE TABLE contact (
    contactId INT IDENTITY(1,1) PRIMARY KEY,
    nickname VARCHAR(50),
    email VARCHAR(100),
    messageContact TEXT,
    createdAt DATE DEFAULT GETDATE()
);


insert into users
	values('NguyenDangPhon', 'personalproject', 'Phon')
