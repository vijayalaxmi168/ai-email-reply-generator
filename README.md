# 🤖 AI Email Reply Generator

A **Spring Boot** web application that uses the **OpenAI API** to automatically generate professional email replies based on the original email content and a selected tone.

Built as a portfolio project demonstrating real-world Java backend skills.

---

## 🚀 Features

- ✅ **AI-Powered Replies** — Integrates with OpenAI GPT API to generate contextual email replies
- ✅ **Tone Selection** — Choose between Professional, Friendly, or Short reply styles
- ✅ **Save to Database** — All generated replies are persisted in MySQL via JPA/Hibernate
- ✅ **Reply History** — View past generated replies on the history page
- ✅ **REST APIs** — Full CRUD API for programmatic access
- ✅ **Responsive UI** — Clean, modern Thymeleaf + Bootstrap 5 interface
- ✅ **Input Validation** — Server-side validation with clear error messages
- ✅ **Global Error Handling** — Clean JSON error responses

---

## 🛠️ Tech Stack

| Technology       | Purpose                         |
|------------------|---------------------------------|
| Java 17          | Programming language            |
| Spring Boot 3.x  | Application framework           |
| Spring Data JPA  | Database abstraction layer      |
| Hibernate        | ORM (maps Java objects to DB)   |
| MySQL            | Relational database             |
| Thymeleaf        | Server-side HTML templating     |
| Lombok           | Reduces boilerplate code        |
| Maven            | Build and dependency management |
| OpenAI API       | AI text generation              |
| Bootstrap 5      | Frontend styling                |

---

## 📁 Project Structure

```
ai-email-reply-generator/
│
├── src/main/java/com/aiemailreply/
│   ├── AiEmailReplyGeneratorApplication.java   ← Main class
│   │
│   ├── config/
│   │   ├── OpenAiConfig.java                   ← RestTemplate + API config
│   │   └── GlobalExceptionHandler.java         ← Centralized error handling
│   │
│   ├── controller/
│   │   ├── EmailReplyController.java            ← REST API endpoints
│   │   └── WebController.java                  ← Thymeleaf page handlers
│   │
│   ├── service/
│   │   ├── EmailReplyService.java               ← Business logic
│   │   └── OpenAiService.java                  ← OpenAI API integration
│   │
│   ├── repository/
│   │   └── EmailReplyRepository.java            ← JPA database queries
│   │
│   ├── entity/
│   │   ├── EmailReply.java                     ← DB entity (email_replies table)
│   │   └── User.java                           ← DB entity (users table)
│   │
│   └── dto/
│       ├── EmailReplyRequestDTO.java            ← Incoming request data
│       └── EmailReplyResponseDTO.java           ← Outgoing response data
│
├── src/main/resources/
│   ├── templates/
│   │   ├── index.html                          ← Main UI page (Thymeleaf)
│   │   └── history.html                        ← Reply history page
│   └── application.properties                  ← App configuration
│
└── pom.xml                                     ← Maven dependencies
```

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+
- OpenAI API Key ([Get one here](https://platform.openai.com/api-keys))

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/your-username/ai-email-reply-generator.git
cd ai-email-reply-generator
```

**2. Create MySQL database**
```sql
CREATE DATABASE email_reply_db;
```

**3. Configure `application.properties`**
```properties
spring.datasource.username=root
spring.datasource.password=your_mysql_password
openai.api.key=sk-your-openai-api-key-here
```

**4. Build and run**
```bash
mvn clean install
mvn spring-boot:run
```

**5. Open the app**
```
http://localhost:8080
```

---

## 📡 REST API Documentation

### Generate Email Reply
```
POST /api/email-replies/generate
Content-Type: application/json

Request Body:
{
  "emailContent": "Hi, I wanted to follow up on the project proposal I sent last week. Could you please let me know if you've had a chance to review it?",
  "tone": "professional"
}

Response (201 Created):
{
  "id": 1,
  "emailContent": "Hi, I wanted to follow up...",
  "tone": "professional",
  "generatedReply": "Dear [Name],\n\nThank you for reaching out. I have reviewed your project proposal and found it quite comprehensive. I would like to schedule a meeting to discuss the details further...",
  "createdAt": "2024-12-01T10:30:00"
}
```

### Get All Replies
```
GET /api/email-replies

Response (200 OK):
[ { ... }, { ... } ]
```

### Get Reply by ID
```
GET /api/email-replies/1

Response (200 OK):
{ "id": 1, "tone": "professional", ... }
```

### Get Replies by Tone
```
GET /api/email-replies/tone/professional
```

---

## 🗄️ Database Schema

```sql
CREATE TABLE email_replies (
  id             BIGINT AUTO_INCREMENT PRIMARY KEY,
  email_content  TEXT NOT NULL,
  tone           VARCHAR(50) NOT NULL,
  generated_reply TEXT,
  created_at     DATETIME
);

CREATE TABLE users (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  email      VARCHAR(150) UNIQUE NOT NULL,
  password   VARCHAR(255) NOT NULL,
  created_at DATETIME
);
```

---

## 📸 Screenshots

| Feature | Description |
|---------|-------------|
| Main Page | Email input form with tone selection |
| Generated Reply | AI reply displayed with copy button |
| History Page | All saved replies with pagination |

---

## 🔮 Future Enhancements

- [ ] User authentication with Spring Security
- [ ] Support multiple AI models (Claude, Gemini)
- [ ] Export replies as PDF or text file
- [ ] Email directly via JavaMailSender
- [ ] React frontend with REST API
- [ ] Docker containerization

---

## 👨‍💻 Author

**Your Name**  
MCA Graduate | Java Backend Developer  
[LinkedIn](https://linkedin.com/in/your-profile) | [GitHub](https://github.com/your-username)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
