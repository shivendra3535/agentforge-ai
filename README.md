# AgentForge AI

> Multi-Agent AI Software Development System built with Java, Spring Boot, Microservices, Apache Kafka, WebSockets, MySQL, and React.

---

## Overview

**AgentForge AI** is a production-style multi-agent orchestration platform where multiple AI-powered services collaborate to generate complete Spring Boot applications from natural language prompts.

A user can submit prompts like:

```text
"Build a CRUD API for Book Management with JWT authentication"
```

The system then orchestrates specialized AI agents that work sequentially and asynchronously to:

* Plan the architecture
* Generate production-ready code
* Debug and review generated files
* Create JUnit test cases
* Generate technical documentation

All progress updates are streamed live to the frontend using WebSockets.

---

## Key Features

* Microservices Architecture using Spring Boot
* API Gateway with JWT Authentication
* Service Discovery using Eureka Server
* Event-Driven Communication using Apache Kafka
* AI Agent Orchestration Pipeline
* Real-Time Progress Tracking with WebSockets + STOMP
* React Frontend Dashboard
* MySQL Persistence Layer
* Dockerized Full System Deployment
* Fault-Tolerant Kafka Pipeline with DLQ Support

---

# System Architecture

```text
                        ┌────────────────────┐
                        │    React Client    │
                        └─────────┬──────────┘
                                  │
                           HTTP + WebSocket
                                  │
                     ┌────────────▼────────────┐
                     │      API Gateway        │
                     │ Spring Cloud Gateway    │
                     └────────────┬────────────┘
                                  │
                     ┌────────────▼────────────┐
                     │      Eureka Server      │
                     │   Service Discovery     │
                     └────────────┬────────────┘
                                  │
                     ┌────────────▼────────────┐
                     │   Orchestrator Service  │
                     │ Workflow Management     │
                     └────────────┬────────────┘
                                  │
                        Apache Kafka Topics
                                  │
     ┌──────────┬──────────┬──────────┬──────────┬──────────┐
     ▼          ▼          ▼          ▼          ▼
 Planner     Coder     Debugger    Tester   Documenter
  Agent       Agent       Agent      Agent      Agent
```

---

# Tech Stack

| Layer                   | Technology              |
| ----------------------- | ----------------------- |
| Backend                 | Java, Spring Boot       |
| Microservices           | Spring Cloud            |
| API Gateway             | Spring Cloud Gateway    |
| Service Discovery       | Eureka Server           |
| Messaging Queue         | Apache Kafka            |
| Authentication          | Spring Security + JWT   |
| Database                | MySQL                   |
| Real-Time Communication | WebSocket + STOMP       |
| Frontend                | React + Tailwind CSS    |
| AI Integration          | OpenAI / Gemini APIs    |
| Containerization        | Docker + Docker Compose |

---

# AI Agent Workflow

## 1. Planner Agent

Analyzes the user requirement and creates a structured development plan.

### Output

* Components list
* File structure
* Tech stack recommendation

---

## 2. Coder Agent

Generates complete Spring Boot source code.

### Generates

* Controllers
* Services
* Repositories
* Entities
* DTOs
* Configurations

---

## 3. Debugger Agent

Reviews generated code and fixes:

* Missing annotations
* Incorrect imports
* Logic issues
* Security problems

---

## 4. Tester Agent

Creates:

* JUnit 5 test cases
* Mockito-based service tests
* Controller tests

---

## 5. Documenter Agent

Generates professional:

* README.md
* API documentation
* Setup instructions
* Environment configuration guide

---

# Microservices

```text
agentforge/
│
├── eureka-server/
├── api-gateway/
├── orchestrator-service/
├── planner-agent/
├── coder-agent/
├── debugger-agent/
├── tester-agent/
├── documenter-agent/
├── shared-dto/
├── llm-client/
└── frontend/
```

---

# Core Functionalities

## API Gateway

* Centralized routing
* JWT validation
* Authentication filtering
* Public/private route management

---

## Eureka Service Discovery

All services dynamically register and discover each other without hardcoded IP addresses.

---

## Kafka Event-Driven Pipeline

### Topics

| Topic                  | Purpose         |
| ---------------------- | --------------- |
| agent-planner-topic    | Planner tasks   |
| agent-coder-topic      | Code generation |
| agent-debugger-topic   | Code review     |
| agent-tester-topic     | Test generation |
| agent-documenter-topic | Documentation   |
| agent-result-topic     | Agent outputs   |
| agent-dlq-topic        | Failed messages |

---

## Real-Time Progress Updates

WebSocket events notify the frontend whenever an agent:

* Starts execution
* Completes execution
* Fails processing

### Example Event

```json
{
  "jobId": 101,
  "agentName": "CODER",
  "status": "COMPLETED",
  "message": "Generated 12 Java files"
}
```

---

# Database Schema

## workflow_jobs

```sql
CREATE TABLE workflow_jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_prompt TEXT NOT NULL,
    status ENUM(
        'PENDING',
        'RUNNING',
        'DONE',
        'PARTIALLY_FAILED',
        'FAILED'
    ),
    created_at DATETIME,
    completed_at DATETIME
);
```

---

## agent_results

```sql
CREATE TABLE agent_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_id BIGINT,
    agent_name VARCHAR(50),
    output_json LONGTEXT,
    status VARCHAR(30)
);
```

---

# Authentication

* JWT-based stateless authentication
* Spring Security filter chain
* Protected API routes
* Public authentication endpoints

---

# Frontend Dashboard

## Features

* User Login
* Prompt Submission
* Live Agent Progress Tracking
* Syntax Highlighted Code Viewer
* Job History Tracking
* Generated README Viewer

---

# Docker Deployment

Run the complete distributed system with a single command:

```bash
docker-compose up
```

Includes:

* MySQL
* Kafka
* Zookeeper
* Kafka UI
* Eureka Server
* API Gateway
* All AI Agents
* React Frontend

---

# Example Workflow

## User Prompt

```text
Build a Student Management REST API with JWT Authentication
```

## System Flow

```text
User Request
    ↓
Planner Agent
    ↓
Coder Agent
    ↓
Debugger Agent
    ↓
Tester Agent
    ↓
Documenter Agent
    ↓
Final Generated Project
```

---

# Error Handling

* Kafka Dead Letter Queue (DLQ)
* Retry Mechanism
* Global Exception Handling
* Validation Layer
* Partial Failure Recovery
* Fault-Tolerant Event Processing

---

# Why This Project Stands Out

Most student projects focus on:

* CRUD APIs
* Basic JWT authentication
* Monolithic architecture

AgentForge AI demonstrates:

* Distributed Systems
* Event-Driven Architecture
* AI Orchestration
* Real-Time Streaming
* Scalable Microservices
* Production-Level Design Patterns

---

# Future Enhancements

* Multi-language code generation
* Kubernetes deployment
* Redis caching
* Agent memory/context persistence
* AI model switching
* Observability with Prometheus + Grafana
* CI/CD pipeline integration

---

# Learning Outcomes

This project demonstrates understanding of:

* Spring Boot Microservices
* Kafka Messaging
* Distributed System Design
* AI Integration in Backend Systems
* WebSockets
* JWT Security
* Dockerized Deployments
* Async Processing
* Fault-Tolerant Architectures

---

# Setup Instructions

## Clone Repository

```bash
git clone https://github.com/your-username/agentforge-ai.git
cd agentforge-ai
```

---

## Configure Environment Variables

```env
OPENAI_API_KEY=your_key
JWT_SECRET=your_secret
MYSQL_USERNAME=root
MYSQL_PASSWORD=root
```

---

## Start Infrastructure

```bash
docker-compose up -d
```

---

## Run Services

```bash
mvn clean install
```

Run each service individually or via Docker Compose.

---

# Demo Flow

1. Login
2. Submit Prompt
3. Watch AI agents execute live
4. View generated code
5. Inspect Kafka topics
6. Check MySQL workflow records

---

# Interview Talking Points

## Why Kafka?

* Decoupled architecture
* Durable messaging
* Replay capability
* Independent service scaling

---

## Why Microservices?

* Independent deployment
* Fault isolation
* Horizontal scalability
* Better modularity

---

## Why WebSockets?

* Real-time UI updates
* Better user experience
* No frontend polling overhead

---

# Project Status

```text
Phase 1 → Foundation & Core Services
Phase 2 → AI Agent Pipeline
Phase 3 → Kafka Event-Driven Architecture
Phase 4 → Tester & Documenter Agents
Phase 5 → React Dashboard
Phase 6 → Docker + Production Polish
```

---

# Author

**Shivendra Pandey**

Java Full Stack Developer | Spring Boot | Microservices | Kafka | AI Systems

---

# License

This project is licensed under the MIT License.

---

# Final Note

AgentForge AI is designed not just as a project, but as a demonstration of production-grade backend engineering, distributed architecture, and modern AI-powered system design.
