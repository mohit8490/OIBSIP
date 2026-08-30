# 📚 DigiLibrary — Digital Library Management System

A modern and user-friendly **Digital Library Management System** developed using **Java Spring Boot, Thymeleaf, MySQL, HTML, CSS and JavaScript**.

DigiLibrary provides separate interfaces for **Users** and **Administrators** to manage books, issues, reservations, fines, queries, notifications and library members through a centralized web application.

---

## ✨ Features

### 👤 User Features

- 🔐 User Registration and Login
- 📊 User Dashboard
- 📚 Browse Available Books
- 🔎 Search and view books
- 📖 Issue Books
- ↩️ Track issued books
- 📋 Reserve unavailable books
- 🔔 View notifications
- ✅ Mark notifications as read
- 🗑️ Delete notifications
- 💰 View fines
- ❓ Submit queries to the administrator
- 💬 Receive responses from the administrator
- 👤 View account information
- 🚪 Logout

---

### 👨‍💼 Admin Features

- 🔐 Admin Login
- 📊 Admin Dashboard
- 📚 Book Management
- ➕ Add New Books
- ✏️ Edit Books
- 🗑️ Manage/Delete Books
- 📦 Manage book quantities and availability
- 📖 Manage issued books
- 📋 Manage reservations
- 👥 Manage library members
- 💰 Fine Management
- ❓ View User Queries
- 💬 Respond to User Queries
- 🔔 Trigger user notifications
- 👤 View administrator account information
- 🚪 Logout

---

# 🔄 Application Workflow

```text
                         📚 DigiLibrary
                              │
              ┌───────────────┴───────────────┐
              │                               │
          👤 USER                         👨‍💼 ADMIN
              │                               │
       ┌──────┼──────┐                ┌───────┼────────┐
       │      │      │                │       │        │
      📚     📖     📋              📚      👥       📖
    Books  Issues  Reserve         Books   Members   Issues
       │      │      │                │       │        │
       └──────┼──────┘                ├───────┼────────┤
              │                       │       │
             🔔                      📋      💰
       Notifications             Reservations Fines
              │                       │
             ❓                       ❓
           Queries ◄────── 💬 ───── Queries
```

---

# 🏗️ Project Architecture

The project follows a layered architecture based on Spring Boot.

```text
                    🌐 Web Browser
                         │
                         ▼
                 🎨 Thymeleaf UI
                         │
                         ▼
                  🎮 Controller
                         │
                         ▼
                    ⚙️ Service
                         │
                         ▼
                  🗃️ Repository
                         │
                         ▼
                    🐬 MySQL
```

### 📌 Architecture Layers

#### 🎮 Controller Layer

Handles incoming HTTP requests and connects the frontend with the service layer.

#### ⚙️ Service Layer

Contains the application's main business logic including:

- Book management
- Book issuing
- Reservations
- Fines
- Queries
- Notifications
- User management

#### 🗃️ Repository Layer

Uses Spring Data JPA to communicate with the MySQL database.

#### 🧩 Model Layer

Contains the application's entities such as:

- User
- Book
- Issue
- Fine
- Query
- Notification
- Book Reservation

#### 🎨 Template Layer

Contains Thymeleaf HTML pages for the User and Admin interfaces.

---

# 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| ☕ Java | Backend programming language |
| 🌱 Spring Boot | Application framework |
| 🌐 Spring MVC | Web application architecture |
| 🗃️ Spring Data JPA | Database operations |
| 🔄 Hibernate | ORM and entity mapping |
| 🐬 MySQL | Relational database |
| 🍃 Thymeleaf | Dynamic HTML rendering |
| 🎨 HTML5 | Web page structure |
| 🎨 CSS3 | User interface styling |
| ⚡ JavaScript | Client-side interactions |
| 📦 Maven | Dependency and build management |
| 💻 VS Code | Development environment |
| 🐬 MySQL Workbench | Database management |
| 🐙 Git | Version control |
| 🐙 GitHub | Source code hosting |

---

# 📁 Project Structure

```text
Java-Task5-DigitalLibraryManagementSystem/
│
├── 📁 src/
│   │
│   └── 📁 main/
│       │
│       ├── 📁 java/
│       │   │
│       │   └── 📁 com/
│       │       └── 📁 oibsip/
│       │           └── 📁 library/
│       │               │
│       │               ├── 📁 controller/
│       │               │   ├── AdminBookController.java
│       │               │   ├── AdminDashboardController.java
│       │               │   ├── AdminFineController.java
│       │               │   ├── AdminIssueController.java
│       │               │   ├── AdminMemberController.java
│       │               │   ├── AdminQueryController.java
│       │               │   ├── AdminReservationController.java
│       │               │   ├── NotificationController.java
│       │               │   └── ...
│       │               │
│       │               ├── 📁 service/
│       │               │   ├── BookService.java
│       │               │   ├── FineService.java
│       │               │   ├── IssueService.java
│       │               │   ├── NotificationService.java
│       │               │   ├── QueryService.java
│       │               │   ├── ReservationService.java
│       │               │   └── ...
│       │               │
│       │               ├── 📁 repository/
│       │               │   ├── BookRepository.java
│       │               │   ├── BookReservationRepository.java
│       │               │   ├── FineRepository.java
│       │               │   ├── IssueRepository.java
│       │               │   ├── NotificationRepository.java
│       │               │   ├── QueryRepository.java
│       │               │   ├── UserRepository.java
│       │               │   └── ...
│       │               │
│       │               ├── 📁 model/
│       │               │   ├── Book.java
│       │               │   ├── BookReservation.java
│       │               │   ├── Fine.java
│       │               │   ├── Issue.java
│       │               │   ├── Notification.java
│       │               │   ├── Query.java
│       │               │   ├── User.java
│       │               │   └── ...
│       │               │
│       │               └── LibraryApplication.java
│       │
│       └── 📁 resources/
│           │
│           ├── 📁 static/
│           │   ├── 📁 css/
│           │   │   └── style.css
│           │   │
│           │   └── 📁 js/
│           │
│           ├── 📁 templates/
│           │   │
│           │   ├── index.html
│           │   ├── login.html
│           │   ├── register.html
│           │   │
│           │   ├── 📁 user/
│           │   │   ├── dashboard.html
│           │   │   ├── books.html
│           │   │   ├── my-books.html
│           │   │   ├── reservations.html
│           │   │   ├── notifications.html
│           │   │   ├── fines.html
│           │   │   └── query.html
│           │   │
│           │   └── 📁 admin/
│           │       ├── dashboard.html
│           │       ├── books.html
│           │       ├── add-book.html
│           │       ├── edit-book.html
│           │       ├── issued-books.html
│           │       ├── reservations.html
│           │       ├── members.html
│           │       ├── fines.html
│           │       └── queries.html
│           │
│           └── application.properties
│
├── 📁 database/
│   └── library_management.sql
│
├── 📁 screenshots/
│   ├── Home Page.png
│   ├── Login Page.png
│   ├── Register.png
│   ├── User Dashboard.png
│   ├── User Browse Books.png
│   ├── User My Books.png
│   ├── User Reservation.png
│   ├── User Notification.png
│   ├── User Query.png
│   ├── User Fine.png
│   ├── User Profile.png
│   ├── Admin Dashboard.png
│   ├── Admin Book Management.png
│   ├── Admin Add New Book.png
│   ├── Admin Edit Book.png
│   ├── Admin Issue Book.png
│   ├── Admin Reservation.png
│   ├── Admin Member Management.png
│   ├── Admin Fine Management.png
│   ├── Admin Query.png
│   └── Admin Profile.png
│
├── 📄 pom.xml
├── 📄 README.md
└── 📄 .gitignore
```

> 📌 The structure above represents the organization of the project. If any filename differs in the repository, the actual project filename should be considered the source of truth.

---

# 🗄️ Database

The application uses **MySQL** for persistent data storage.

### 🛢️ Database Name

```text
library_management
```

### 📊 Main Tables

```text
users
books
issues
book_reservations
fines
queries
notifications
```

### 🔗 Database Relationships

```text
                 👤 USERS
                    │
          ┌─────────┼─────────┐
          │         │         │
          ▼         ▼         ▼
       📖 ISSUES  📋 RESERVATIONS
          │         │
          │         │
          ▼         ▼
       📚 BOOKS ◄───┘
          │
          │
          ▼
       💰 FINES


       👤 USER
          │
          ├──── ❓ QUERIES
          │
          └──── 🔔 NOTIFICATIONS
```

A SQL script is also included in:

```text
database/library_management.sql
```

This allows the database structure to be viewed directly from the GitHub repository.

---

# 📚 Book Management

Administrators can manage the complete library collection.

### Admin can:

- ➕ Add books
- ✏️ Edit book information
- 🗑️ Manage books
- 📦 Update total quantity
- 📊 Track available quantity
- 📖 Monitor issued books

Book information includes:

```text
Book ID
Title
Author
ISBN
Category
Total Quantity
Available Quantity
```

---

# 📖 Book Issue System

Users can issue books that are currently available.

```text
👤 User
   │
   ▼
📚 Browse Books
   │
   ▼
📖 Select Available Book
   │
   ▼
📌 Issue Book
   │
   ▼
📖 My Books
```

The system maintains:

- Issue date
- Due date
- Return date
- Issue status
- User information
- Book information

---

# 📋 Book Reservation System

When a book is unavailable, users can reserve it.

```text
📚 Book
   │
   ├── Available
   │      ↓
   │   📖 Issue
   │
   └── Unavailable
          ↓
      📋 Reserve
          ↓
    Active Reservation
```

The reservation system also checks whether:

- The user already has the book issued
- The user already has an active reservation
- The book is currently available

---

# 💰 Fine Management

The system provides fine management for overdue library books.

Fine information can include:

```text
👤 User
📚 Book
📅 Issue Date
📅 Due Date
📅 Return Date
💰 Fine Amount
📌 Fine Status
```

### User Side

```text
User Dashboard
      ↓
My Fines
      ↓
View Fine Information
```

### Admin Side

```text
Admin Dashboard
      ↓
Fine Management
      ↓
View Fine Records
```

---

# 🔔 Notification System

DigiLibrary includes a notification system for communicating important updates to users.

Example:

```text
👤 User
   │
   ▼
❓ Submit Query
   │
   ▼
👨‍💼 Admin
   │
   ▼
💬 Respond
   │
   ▼
🔔 Notification Created
   │
   ▼
👤 User
```

Users can:

- 🔔 View notifications
- 🔢 See unread notification count
- ✅ Mark individual notifications as read
- ✅ Mark all notifications as read
- 🗑️ Delete notifications

---

# ❓ User Query System

Users can communicate directly with the library administrator.

### 👤 User

```text
User Dashboard
      ↓
Ask a Query
      ↓
Enter Query
      ↓
Submit
```

### 👨‍💼 Admin

```text
Admin Dashboard
      ↓
User Queries
      ↓
View Query
      ↓
Write Response
      ↓
Respond
```

### 🔔 Notification

After the administrator responds:

```text
Admin Response
      ↓
Notification
      ↓
User Notification Page
```

---

# 👥 Member Management

Administrators can view registered library members.

Member information includes:

- 👤 Name
- 🆔 Username
- 📧 Email
- 🔐 Role
- 📚 Library activity

---

# 👨‍💼 Admin Dashboard

The Admin Dashboard provides an overview of the library system.

It includes statistics such as:

- 📚 Total Books
- 👥 Total Members
- 📖 Currently Issued Books
- 💰 Total Fine Records
- 🔴 Unpaid Fine Records
- 💵 Total Unpaid Fine Amount

The dashboard also provides access to:

```text
📚 Manage Books
📖 Issued Books
📋 Reservations
👥 Members
💰 Fine Management
❓ User Queries
```

---

# 👤 User Dashboard

The User Dashboard provides access to all major library services.

### Library Services

```text
📚 Books
📖 Issued Books
📋 Reservations
🔔 Notifications
💰 Fines
❓ Help / Queries
```

The dashboard also contains account information accessible through the user profile icon.

---

# 👤 Account Information

Both User and Admin interfaces provide account information through the profile section.

### User Profile

```text
👤 Name
🆔 Username
📧 Email
🔐 Role
🔔 Unread Notifications
🚪 Logout
```

### Admin Profile

```text
👨‍💼 Name
🆔 Username
📧 Email
🔐 Role
🚪 Logout
```

---

# 🎨 User Interface

The application provides separate dashboard layouts for users and administrators.

### 🎨 UI Highlights

- Clean dashboard layout
- Sidebar navigation
- Top navigation bar
- Profile dropdown
- Cards and panels
- Responsive layout
- Consistent buttons
- Status indicators
- User-friendly forms
- Clear navigation
- Library-focused visual design

---

# 📸 Screenshots

The repository contains screenshots demonstrating the major features of the application.

## 🏠 General Pages

| Screenshot | Description |
|---|---|
| 🏠 Home Page | DigiLibrary landing page |
| 🔐 Login Page | User/Admin login |
| 📝 Register | New user registration |

---

## 👤 User Screenshots

| Screenshot | Description |
|---|---|
| 📊 User Dashboard | User dashboard and library services |
| 📚 User Browse Books | Available books |
| 📖 User My Books | Issued books |
| 📋 User Reservation | User reservations |
| 🔔 User Notification | User notifications |
| ❓ User Query | Query submission |
| 💰 User Fine | Fine information |
| 👤 User Profile | User account details |

---

## 👨‍💼 Admin Screenshots

| Screenshot | Description |
|---|---|
| 📊 Admin Dashboard | Library statistics and management |
| 📚 Admin Book Management | Book management |
| ➕ Admin Add New Book | Add book form |
| ✏️ Admin Edit Book | Edit book form |
| 📖 Admin Issue Book | Issued book management |
| 📋 Admin Reservation | Reservation management |
| 👥 Admin Member Management | Registered members |
| 💰 Admin Fine Management | Fine records |
| ❓ Admin Query | User query management |
| 👨‍💼 Admin Profile | Administrator account details |

All screenshots are available in:

```text
📁 screenshots/
```

---

# 🚀 How to Run

## 1️⃣ Clone the Repository

```bash
git clone https://github.com/mohit8490/OIBSIP.git
```

---

## 2️⃣ Open the Project

Open the project in **VS Code** or another Java IDE.

```text
Java-Task5-DigitalLibraryManagementSystem
```

---

## 3️⃣ Configure MySQL

Create the database:

```sql
CREATE DATABASE library_management;
```

The SQL script is available at:

```text
database/library_management.sql
```

---

## 4️⃣ Configure Database Connection

Open:

```text
src/main/resources/application.properties
```

Configure your local MySQL credentials.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_management
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> ⚠️ Do not upload real database passwords or sensitive credentials to GitHub.

---

## 5️⃣ Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

### Windows Maven Wrapper

```powershell
.\mvnw.cmd spring-boot:run
```

---

## 6️⃣ Open the Application

After the application starts, open:

```text
http://localhost:8080
```

---

# 🧪 Testing Workflows

## 👤 User Registration

```text
Register
   ↓
Create Account
   ↓
Login
   ↓
User Dashboard
```

---

## 📚 Issue Book

```text
Browse Books
      ↓
Select Available Book
      ↓
Issue Book
      ↓
My Books
```

---

## 📋 Reserve Book

```text
Browse Books
      ↓
Book Unavailable
      ↓
Reserve Book
      ↓
Reservations
```

---

## ❓ Query and Response

```text
USER
  │
  ▼
Submit Query
  │
  ▼
ADMIN
  │
  ▼
View Query
  │
  ▼
Respond
  │
  ▼
USER
  │
  ▼
🔔 Notification
```

---

## 💰 Fine Management

```text
📖 Book Issued
      ↓
📅 Due Date
      ↓
⏰ Overdue
      ↓
💰 Fine
      ↓
👤 User Fine Information
      ↓
👨‍💼 Admin Fine Management
```

---

# 🔐 Roles

The system supports two main roles.

### 👤 USER

Library member who can:

- Browse books
- Issue books
- Reserve books
- View issued books
- View notifications
- View fines
- Submit queries

### 👨‍💼 ADMIN

Library administrator who can:

- Manage books
- Manage members
- Manage issues
- Manage reservations
- Manage fines
- Manage queries
- Respond to users

---

# 📌 Key Project Highlights

✅ Separate User and Admin dashboards  
✅ Role-based application flow  
✅ Complete book management  
✅ Book issue management  
✅ Book reservation system  
✅ Fine management  
✅ Notification system  
✅ Query and response system  
✅ Member management  
✅ Book availability tracking  
✅ MySQL database integration  
✅ Spring Data JPA  
✅ Hibernate ORM  
✅ Thymeleaf templates  
✅ HTML/CSS/JavaScript frontend  
✅ Maven project management  
✅ Git/GitHub version control  
✅ Account/profile information  
✅ Responsive and clean UI  

---

# 📂 Important Files

| File / Folder | Description |
|---|---|
| `src/main/java/` | Backend Java source code |
| `controller/` | Controllers and request handling |
| `service/` | Application business logic |
| `repository/` | Database repositories |
| `model/` | JPA entities/models |
| `templates/` | Thymeleaf HTML pages |
| `static/css/` | CSS styling |
| `static/js/` | JavaScript files |
| `application.properties` | Application and database configuration |
| `database/library_management.sql` | Database SQL script |
| `screenshots/` | Application screenshots |
| `pom.xml` | Maven dependencies/configuration |
| `README.md` | Project documentation |

---

# 🎯 Project Objective

The objective of DigiLibrary is to create a centralized digital platform that simplifies everyday library operations.

Instead of manually managing:

```text
📚 Books
👥 Members
📖 Issues
📋 Reservations
💰 Fines
❓ Queries
🔔 Notifications
```

the system provides a single web-based platform for managing these activities efficiently.

---

# 🌱 Future Enhancements

Possible future improvements include:

- 🔐 Spring Security integration
- 🔑 Password encryption
- 📧 Email notifications
- 📱 Improved mobile responsiveness
- 🔎 Advanced book search
- 📊 Advanced analytics and reports
- ⏰ Automated due-date reminders
- 💳 Online fine payment
- ☁️ Cloud deployment
- 📈 Library usage reports
- 📚 Book recommendation system

---

# 🧑‍💻 Development

This project was developed as part of the **OIBSIP Java Development Internship**.

### 📌 Project

**Java Task 5 — Digital Library Management System**

### 💻 Core Technologies

```text
Java
Spring Boot
Spring MVC
Spring Data JPA
Hibernate
MySQL
Thymeleaf
HTML
CSS
JavaScript
Maven
Git
GitHub
```

---

# 🤝 Contribution

Contributions and suggestions are welcome.

If you want to improve the project:

```text
1. Fork the repository
2. Create a new branch
3. Make your changes
4. Commit your changes
5. Push the branch
6. Create a Pull Request
```

---

# ⭐ Support

If you find this project useful or interesting, consider giving the repository a ⭐ on GitHub.

---

# 👨‍💻 Author

**Mohit Choudhary**

📚 **Digital Library Management System**

Built with ☕ **Java + Spring Boot** and ❤️ for software development.

---

## 📜 License

This project was created as an educational/internship project for learning and demonstrating Java Spring Boot, database management and full-stack web application development.