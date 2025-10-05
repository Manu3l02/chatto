# 📌 Chatto 💬

[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=plastic&logo=openjdk&logoColor=white)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=plastic&logo=springboot&logoColor=fff)](#)
[![React](https://img.shields.io/badge/React-%2320232a.svg?style=plastic&logo=react&logoColor=%2361DAFB)](#)
[![Vite](https://img.shields.io/badge/Vite-646CFF?style=plastic&logo=vite&logoColor=fff)](#)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind%20CSS-%2338B2AC.svg?style=plastic&logo=tailwind-css&logoColor=white)](#)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=plastic&logo=mysql&logoColor=fff)](#)
[![Git](https://img.shields.io/badge/GitHub-181717?style=plastic&logo=github&logoColor=white)](#)


A **full-stack chat application** built with **Spring Boot** (backend) and **React + Vite + TailwindCSS** (frontend).  
Supports JWT authentication, WebSocket with STOMP for real-time messaging, and message persistence.

> 🇮🇹 [Read this README in Italian](./README.it.md)

---

## 🚀 Features

- **Authentication & Security**
  - JWT-based login
  - Token stored in `localStorage`
- **Realtime Chat**
  - Communication via **WebSocket (STOMP)**
  - Send and receive messages instantly
- **Message Persistence**
  - Messages are stored in DB and reloaded when opening the chat
- **User Interface**
  - Sidebar with chat list
  - Chat window with **bubbles style**
  - Own messages aligned to the right, others to the left
  - Timestamp next to each message
  - Auto-scroll to the latest message
- **Clean architecture**
  - Backend in `backend/` (Spring Boot + JPA + Security)
  - Frontend in `frontend/` (React + Vite + Tailwind)
  - Centralized API calls (`apiClient.js`)

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot (Web, Security, JPA, WebSocket)
- JWT Authentication
- Maven
- MySQL (default, configurable whit other database)

### Frontend
- React + Vite
- TailwindCSS
- STOMP.js (for WebSocket)

---

## ⚙️ Setup

### 1️⃣ Clone the project
```bash
git clone https://github.com/<YOUR-USERNAME>/chatto.git
cd chatto
```

### 2️⃣ Start the backend
```bash
cd backend
./mvnw spring-boot:run
```
Backend will be available at http://localhost:8080

### 3️⃣ Start the frontend
```bash
cd frontend
npm install
npm run dev
```
Frontend will be available at http://localhost:5173

## 📸 Screenshots

(Add UI images)

## 📌 Roadmap

- [X] JWT Authentication
- [X] 1-to-1 Chat
- [X] Message Persistence
- [ ] Group Chats with custom names
- [ ] UI/UX improvements (animations, themes, etc.)

## 👨‍💻 Author

Manuel (@Manu3l02)
💡 A full-stack chat app as a personal project to learn WebSocket.
