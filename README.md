# 📚 Book Management System (MVP Online Library)

## Overview
This project is an **MVP Online Library System** with three main modules:
1. **Book Management**
2. **User Management (Authentication)**
3. **Check-In / Check-Out Management**

It is built using **Spring Boot + PostgreSQL** and demonstrates core features of a digital library where Librarians (Admins) and Readers can interact with books.

---

## 🚀 Features

### 👤 User Management
- User Registration (Reader & Admin/Librarian)
- Authentication (Login/Logout with JWT)
- Password Reset & Email Verification
- Upload profile photo
- Default **Librarian/Admin** created on startup

### 📖 Book Management
- Upload books with:
    - Cover Image
    - Title
    - ISBN
    - Revision number
    - Published date
    - Publisher
    - Author(s)
    - Genre
    - Date added
- Update/modify book details
- Search books by:
    - Title
    - ISBN
    - Publisher
    - Date added
- View list of checked-out books with reader details and due dates

### 🔄 Check-Out / Check-In Management
- Readers can **check-out** books (10 days limit).
- Readers can **check-in** (return) books.
- Email Notifications:
    - Reminder 2 days before check-in due date.
    - Alert to Librarian if overdue.

---

## 🛠️ Tech Stack
- **Java 17+**
- **Spring Boot 3+**
- **PostgreSQL**
- **Hibernate / JPA**
- **JWT Authentication**
- **Spring Mail (Email Notifications)**

---

## ⚙️ Installation & Setup

### 1. Clone Repository
```bash
git clone https://github.com/your-username/book-management.git
cd book-management
# Book Management
