# Online Voting Application (Java Swing + JDBC)

## Overview

The **Online Voting Application** is a desktop-based system developed using **Java Swing and AWT** for building the graphical user interface and **JDBC (Java Database Connectivity)** for interacting with a **MySQL database**. The purpose of this project is to simulate a simple electronic voting system where users can securely register, log in, and cast their vote for a candidate through an intuitive graphical interface.

The application is designed to demonstrate how **Java GUI development can be integrated with database operations** to create a functional software system. All voter information and voting records are stored in a relational database, allowing the system to manage users efficiently and maintain persistent data between sessions.

A key feature of the system is the **prevention of duplicate voting**. Each user is associated with a voting status in the database, and once a vote is cast, the system updates this status to ensure that the same user cannot vote again. This helps maintain the integrity and fairness of the voting process.

The application follows a **modular design**, separating responsibilities into different components such as database connectivity, user authentication, registration handling, voting functionality, and result display. The GUI provides a simple and user-friendly workflow that guides users through registration, login, voting, and viewing results.

Overall, this project demonstrates practical concepts such as **event-driven programming, GUI design using Swing, database integration using JDBC, and basic application workflow management**, making it a useful example of how Java can be used to build interactive desktop applications.

---

## Features

* User registration system
* Login authentication
* Voting interface for selecting candidates
* Votes stored in a MySQL database
* Prevention of duplicate voting
* Display of voting results
* Simple and user-friendly GUI

---

## Technologies Used

* Java
* Java Swing
* Java AWT
* JDBC
* MySQL

---

## Project Structure

```id="pjymr9"
OnlineVotingApplication
│
├── Main.java
├── DBConnection.java
├── LoginPage.java
├── RegisterPage.java
├── VotingPage.java
├── ResultPage.java
│
├── lib
│   └── mysql-connector-j-9.6.0.jar
│
└── run.bat
```

---

## Application Windows

The application contains the following GUI screens:

* Login Window
* Registration Window
* Voting Window
* Results Window

(Screenshots of the application windows and database structure are included separately.)

---

## How the System Works

1. Start the application using the provided `run.bat` file.
2. New users register through the registration window.
3. Users log in with their credentials.
4. If the user has not voted yet, the voting interface opens.
5. The user selects a candidate and submits their vote.
6. The vote count is updated in the database.
7. The system marks the user as having voted to prevent duplicate voting.
8. The results window displays the vote counts for each candidate.

---

## Workflow

```id="wfp38t"
Start Application
      ↓
Login Page
      ↓
Register (if new user)
      ↓
Login
      ↓
Voting Page
      ↓
Submit Vote
      ↓
Results Page
```

---

## Future Improvements

* Admin panel for managing candidates
* Password encryption for improved security
* Email verification for voters
* Web-based version of the voting system

---

## Author

This project was developed as part of a **Java GUI and Database Connectivity project using Swing and JDBC**.


Group project 
Online voting application by:-

  Shashank Singh : 24BCE10827
  Aaryav Maheshwari : 24BCE10350
  Ashika Ojha : 24BCE10210
  Prerna kalia : 24BCE10219
  Tanisha Tiwari : 24BCE11279
