DROP DATABASE IF EXISTS phon;

CREATE DATABASE phon;
USE phon;

CREATE TABLE users(
	userId INT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(50) NOT NULL,
	password_hash VARCHAR(255) NOT NULL,
	roleUser VARCHAR(15) CHECK(roleUser IN ('Phon', 'Anonymous'))
);

CREATE TABLE post(
	postId INT AUTO_INCREMENT PRIMARY KEY,
	userId INT,
	content TEXT,
	createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
	imageUrl VARCHAR(200),
	FOREIGN KEY (userId) REFERENCES users(userId)
);

CREATE TABLE comments(
	commentId INT AUTO_INCREMENT PRIMARY KEY,
	postId INT,
	userId INT,
	content TEXT,
	createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (postId) REFERENCES post(postId),
	FOREIGN KEY (userId) REFERENCES users(userId)
);

CREATE TABLE contact (
    contactId INT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(50),
    email VARCHAR(100),
    messageContact TEXT,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (username, password_hash, roleUser)
VALUES ('NguyenDangPhon', 'personalproject', 'Phon');
