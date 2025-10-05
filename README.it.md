# 📌 Chatto 💬

[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=plastic&logo=openjdk&logoColor=white)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=plastic&logo=springboot&logoColor=fff)](#)
[![React](https://img.shields.io/badge/React-%2320232a.svg?style=plastic&logo=react&logoColor=%2361DAFB)](#)
[![Vite](https://img.shields.io/badge/Vite-646CFF?style=plastic&logo=vite&logoColor=fff)](#)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind%20CSS-%2338B2AC.svg?style=plastic&logo=tailwind-css&logoColor=white)](#)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=plastic&logo=mysql&logoColor=fff)](#)
[![Git](https://img.shields.io/badge/GitHub-181717?style=plastic&logo=github&logoColor=white)](#)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=plastic&logo=apache-maven&logoColor=white)](#)

Un'applicazione di chat **full-stack** sviluppata con **Spring Boot** (backend) e **React + Vite + TailwindCSS** (frontend).  
Supporta autenticazione JWT, WebSocket con STOMP per messaggistica in tempo reale e persistenza dei messaggi.

> 🇬🇧 [Read this README in English](./README.md)

---

## 🚀 Features

- **Autenticazione & Sicurezza**
  - Login con JWT
  - Token salvato in `localStorage`
- **Chat Realtime**
  - Comunicazione tramite **WebSocket (STOMP)**
  - Invio e ricezione messaggi in tempo reale
- **Persistenza Messaggi**
  - I messaggi vengono salvati in DB e ricaricati quando si apre la chat
- **Interfaccia Utente**
  - Sidebar con lista chat
  - Finestra chat con **bolle stile WhatsApp/Telegram**
  - I propri messaggi sono allineati a destra, quelli degli altri a sinistra
  - Timestamp accanto a ogni messaggio
  - Scroll automatico verso l’ultimo messaggio
- **Architettura pulita**
  - Backend in `backend/` (Spring Boot + JPA + Security)
  - Frontend in `frontend/` (React + Vite + Tailwind)
  - API centralizzate (`apiClient.js`)

---

## 🛠️ Tech Stack

### Backend
- Java 17
- Spring Boot (Web, Security, JPA, WebSocket)
- JWT Authentication
- Maven
- MySQL (default, configurabile con altri database)

### Frontend
- React + Vite
- TailwindCSS
- STOMP.js (per WebSocket)

---

## ⚙️ Setup

### 1️⃣ Clona il progetto
```bash
git clone https://github.com/<TUO-USERNAME>/chatto.git
cd chatto
```

### 2️⃣ Avvia il backend
```bash
cd backend
./mvnw spring-boot:run
```

Il backend sarà disponibile su http://localhost:8080

### 3️⃣ Avvia il frontend
```bash
cd frontend
npm install
npm run dev
```

Il frontend sarà disponibile su http://localhost:5173

## 📸 Screenshots

(Aggiungere immagini UI)

## 📌 Roadmap

 - [X] Autenticazione con JWT
 - [X] Chat 1-to-1
 - [X] Persistenza messaggi
 - [ ] Chat di gruppo con nomi personalizzati
 - [ ] Miglioramenti UI/UX (animazioni, temi, ecc.)


## 👨‍💻 Autore

Manuel (@Manu3l02)
💡 App di chat full-stack come progetto personale per imparare WebSocket.
