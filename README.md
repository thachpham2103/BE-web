# Smart Student Attendance System (Backend)

The **Smart Student Attendance System** is a Learning Management System (LMS) that integrates **Artificial Intelligence (AI)**, **Face Recognition**, and **GPS Geofencing** to provide a secure and automated attendance solution for educational institutions.

The system is designed for both **Lecturers** and **Students**, helping automate attendance, prevent fraudulent check-ins, and provide a comprehensive platform for classroom management, communication, and learning activities.

---

# Core Features

## 1. Smart Attendance System

The attendance module combines AI and location-based technologies to ensure secure and accurate attendance.

### Face Recognition

- AI-powered student identity verification.
- Fast and accurate facial matching.
- Prevents proxy attendance.

### GPS Geofencing

- Validates the student's location before attendance.
- Allows attendance only within the authorized classroom radius.

### Face Anti-Spoofing & Liveness Detection

- Detects fake faces from printed photos or digital screens.
- Verifies that the detected face belongs to a real person.
- Prevents photo, replay, and presentation attacks.

### Real-Time Attendance

- Records attendance in real time.
- Automatically classifies attendance status:
  - Present
  - Late
  - Absent
- Stores complete attendance history.

---

## 2. Learning Management System (LMS)

### Features for Lecturers

- Create and manage class sessions.
- Configure attendance schedules and GPS radius.
- Manage student lists and attendance statistics.
- View attendance percentage for each student.
- Create announcements for classes and courses.
- Publish blogs and discussion posts.
- Real-time group chat with students.
- Review and approve attendance appeal requests.
- Manage course payment status.
- Export attendance reports to Excel.

### Features for Students

- View schedules, timetables, and enrolled courses.
- Check in using Face Recognition and GPS verification.
- View attendance history.
- Submit attendance appeal requests.
- Join class group chats.
- Read and interact with blog posts.
- Receive announcements and attendance reminders.
- View tuition payment status and payment history.

---
# Tech Stack

### Backend

- Java 17
- Spring Boot 3.x
  - Spring MVC
  - Spring Security
  - Spring Data JPA
  - Spring WebSocket
- Hibernate
- MySQL
- Redis
- JWT Authentication

### AI Service

- Python
- OpenCV
- Face Recognition
- Face Anti-Spoofing
- Face Liveness Detection

### Tools & Services

- Docker
- Docker Compose
- Maven
- Lombok
- MapStruct
- Cloudinary API
- SendGrid API
- Springdoc OpenAPI (Swagger)

---

# Getting Started

## Prerequisites

Before running the project, make sure the following software is installed:

- JDK 17+
- Maven
- MySQL
- Redis
- Python 3.10+ (AI Service)
- Docker & Docker Compose (Optional)

---

## Project Architecture

```
Backend (Spring Boot)
│
├── Authentication (JWT)
├── User Management
├── Attendance Management
├── Course Management
├── Payment Management
├── Blog & Announcement
├── Group Chat (WebSocket)
├── Report & Excel Export
│
└── AI Service (Python)
      ├── Face Recognition
      ├── Face Anti-Spoofing
      └── Face Liveness Detection
```

---

# Highlights

- AI-powered Face Recognition
- Face Anti-Spoofing & Liveness Detection
- GPS Geofencing Attendance
- JWT Authentication & RBAC
- Real-time Group Chat
- Attendance Appeal Workflow
- Course Payment Management
- Excel Report Export
- Dockerized Deployment
- RESTful API Architecture

---

# License

This project is developed for educational and research purposes.

3. Luồng hoạt động

<img width="402" height="493" alt="Ảnh chụp màn hình 2026-04-26 225315" src="https://github.com/user-attachments/assets/073749da-bec7-4ee7-b6bc-ecd32175d712" />


