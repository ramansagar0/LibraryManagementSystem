# 📚 Library Management System

A Java-based Library Management System designed to efficiently manage books, members, and book issue/return records.

The system provides a simple graphical user interface using Java Swing and stores data in a MySQL database using JDBC.

---

## ✨ Features

### 🔐 Login System
- Database-based user login
- Username and password authentication
- Login validation

### 📚 Book Management
- Add new books
- View all books
- Search books by title, author, or category
- Update book details
- Delete books
- Track available quantity

### 👥 Member Management
- Add members
- View all members
- Search members
- Update member details
- Delete members

### 🔄 Issue & Return Management
- Issue books to members
- Return books
- Automatically update book availability
- Store issue and return dates
- Prevent issuing books when quantity is unavailable

### 📊 Dashboard
- Total book titles
- Available books
- Total members
- Currently issued books
- Refresh statistics

---

## 🛠️ Technologies Used

- **Java 17**
- **Java Swing** – Graphical User Interface
- **MySQL** – Database
- **JDBC** – Database connectivity
- **Maven** – Project and dependency management
- **Git & GitHub** – Version control

---

## 📁 Project Structure

```text
LibraryManagementSystem
│
├── database
│   └── library.sql
│
├── src
│   └── main
│       └── java
│           └── com
│               └── library
│                   ├── Main.java
│                   │
│                   ├── model
│                   │   ├── Book.java
│                   │   ├── Member.java
│                   │   ├── IssueRecord.java
│                   │   └── User.java
│                   │
│                   ├── dao
│                   │   ├── BookDAO.java
│                   │   ├── MemberDAO.java
│                   │   ├── IssueRecordDAO.java
│                   │   └── UserDAO.java
│                   │
│                   ├── service
│                   │   ├── BookService.java
│                   │   ├── MemberService.java
│                   │   └── IssueService.java
│                   │
│                   ├── database
│                   │   └── DatabaseConnection.java
│                   │
│                   └── ui
│                       ├── LoginFrame.java
│                       ├── Dashboard.java
│                       ├── BookFrame.java
│                       ├── MemberFrame.java
│                       └── IssueReturnFrame.java
│
├── pom.xml
├── README.md
└── .gitignore