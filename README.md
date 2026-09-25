🎯 Task Tracker API

🛠️ Текущий фокус разработки: В данный момент проект находится в стадии активной доработки. Основной приоритет отдается обнаружению и исправлению багов, оптимизации ключевых модулей и повышению общей стабильности и надежности работы приложения.

📌 О проекте

Task Tracker API — это бэкенд-сервис для управления задачами и проектами, созданный на базе Spring Boot.

Проект разрабатывается с целью практики в проектировании чистой многослойной архитектуры (Controller — Service — Repository), реализации RESTful API, безопасной аутентификации, а также связки бэкенда с десктопным клиентом на JavaFX.

💡 Основные идеи и функционал

[x] Архитектура: Разделение приложения на четкие слои (Controller, Service, Repository)

[x] Безопасность: Аутентификация и авторизация пользователей (Spring Security / JWT)

[x] Управление задачами: REST API для создания, чтения, обновления статусов и удаления задач

[x] DTO & Mapping: Использование MapStruct для трансформации объектов и JsonNullable для корректной обработки PATCH-запросов (частичное обновление)

[x] Качество кода: Автоматическое форматирование и проверка стиля с помощью Spotless

[х] Миграции БД: Подключение и настройка миграций PostgreSQL с помощью Liquibase

[х] Тестирование: Покрытие бизнес-логики юнит- и интеграционными тестами (JUnit 5, Mockito)

[x] Контейнеризация: Полноценный запуск приложения и рабочей среды через Docker / Dockerfile

[x] CI/CD Pipeline: Настроен автоматический конвейер сборки, проверки форматов, прогона тестов и сборки Docker-образа при помощи GitHub Actions

[ ] Интеграция: Полная связка REST API с клиентским приложением на JavaFX

🛠 Технологический стек

Language: Java

Framework: Spring Boot, Spring Data JPA, Spring Security

Database: PostgreSQL

Tools & Libraries: MapStruct, JsonNullable, Maven / Gradle

Testing: JUnit 5, Mockito

DevOps & CI/CD: Docker, GitHub Actions

Client UI: JavaFX

🗺 Планы по развитию (Roadmap)

[x] Исправление выявленных ошибок (включая работу с ссылками TaskTag)

[x] Добавление автотестов для контроллеров и сервисов

[x] Добавление Dockerfile и интеграция Docker

[x] Настройка CI/CD для автоматического тестирования и сборки

[ ] Настройка Docker Compose (PostgreSQL + Spring Boot app)

[ ] Доработка и интеграция UI-клиента на JavaFX

## 🚀 Инструкция по запуску

### Предварительные требования
Убедитесь, что у вас установлены:
* **Java SDK 21**
* **PostgreSQL** (версии 14 и выше)
* **Git**

### Запуск через контейнер

1. **Клонируйте репозиторий:**
   
<img width="720" height="250" alt="carbon(3)" src="https://github.com/user-attachments/assets/9da665db-a543-47a9-9ca3-362922a9e183" />

2. ***Соберите Docker-образ:***

<img width="300" height="190" alt="carbon(7)" src="https://github.com/user-attachments/assets/77a60050-fe5f-4611-bfe0-8515ad147750" />

3. ***Запустите контейнер:***

<img width="370" height="210" alt="carbon(8)" src="https://github.com/user-attachments/assets/929577a2-93d4-4a09-9a52-75c720fb8913" />



### Шаги для локального запуска

1. **Клонируйте репозиторий:**
   
<img width="720" height="250" alt="carbon(3)" src="https://github.com/user-attachments/assets/9da665db-a543-47a9-9ca3-362922a9e183" />

   
3. Создайте базу данных (через psql или через IDE:
  psql -U postgres -c "CREATE DATABASE task_tracker_db;"

4. Настройте подключение:
  Проверьте и укажите ваши параметры подключения в src/main/resources/application.yml:
  <img width="400" height="190" alt="carbon" src="https://github.com/user-attachments/assets/9c36842e-e1ee-4b16-af82-0aedc6aec87d" />

5. Запуск
   
  Windows (CMD / PowerShell):

   <img width="200" height="150" alt="carbon(1)" src="https://github.com/user-attachments/assets/341c7d99-0395-4370-baad-4a06b94164b1" />
    
  Linux / macOS:

   <img width="200" height="150" alt="carbon(2)" src="https://github.com/user-attachments/assets/ca645ba3-196c-4ce3-8062-9e136c71d483" />
     


    
  

