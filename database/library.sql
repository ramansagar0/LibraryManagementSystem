CREATE DATABASE library_management;
SHOW DATABASES;
USE library_management;
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);
SHOW TABLES;
CREATE TABLE books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    quantity INT NOT NULL DEFAULT 1
);
SHOW TABLES;
CREATE TABLE members (
    member_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15)
);
SHOW TABLES;
CREATE TABLE issue_records (
    issue_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    issue_date DATE NOT NULL,
    return_date DATE,

    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (member_id) REFERENCES members(member_id)
);
SHOW TABLES;
USE library_management;

SELECT * FROM books;
SELECT * FROM issue_records;

USE library_management;

SELECT * FROM books;

SELECT * FROM members;

SELECT * FROM issue_records;

USE library_management;

INSERT INTO users (username, password)
VALUES ('admin', 'admin123');

SELECT * FROM users;

