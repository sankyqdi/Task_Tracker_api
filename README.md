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

[ ] Качество и валидация: Централизованная обработка исключений (@ControllerAdvice) и валидация входных данных

[ ] Миграции БД: Подключение и настройка миграций PostgreSQL с помощью Liquibase

[ ] Тестирование: Покрытие бизнес-логики юнит- и интеграционными тестами (JUnit 5, Mockito)

[ ] Интеграция: Полная связка REST API с клиентским приложением на JavaFX

🛠 Технологический стек

Language: Java

Framework: Spring Boot, Spring Data JPA, Spring Security

Database: PostgreSQL

Tools & Libraries: MapStruct, JsonNullable, Maven / Gradle

Testing: JUnit 5, Mockito

Client UI: JavaFX

🗺 Планы по развитию (Roadmap)

[ ] Проведение рефакторинга и исправление выявленных ошибок

[ ] Оптимизация работы БД и запросов

[ ] Контейнеризация проекта с помощью Docker Compose

[ ] Настройка CI/CD для автоматического тестирования и сборки

## 🚀 Инструкция по запуску

### Предварительные требования
Убедитесь, что у вас установлены:
* **Java SDK 21**
* **PostgreSQL** (версии 14 и выше)
* **Git**

### Шаги для локального запуска

1. **Клонируйте репозиторий:**
   ```bash
   git clone [https://github.com/sankyqdi/Task_Tracker_api.git](https://github.com/sankyqdi/Task_Tracker_api.git)
   cd Task_Tracker_api
   
2. Создайте базу данных (через psql или через IDE:
  psql -U postgres -c "CREATE DATABASE task_tracker_db;"

3. Настройте подключение:
  Проверьте и укажите ваши параметры подключения в src/main/resources/application.yml:
  <img width="300" height="190" alt="carbon" src="https://github.com/user-attachments/assets/9c36842e-e1ee-4b16-af82-0aedc6aec87d" />

4. Запуск 
  Windows (CMD / PowerShell):

    <img width="200" height="150" alt="carbon(1)" src="https://github.com/user-attachments/assets/341c7d99-0395-4370-baad-4a06b94164b1" />
    
  Linux / macOS:

     <img width="200" height="150" alt="carbon(2)" src="https://github.com/user-attachments/assets/ca645ba3-196c-4ce3-8062-9e136c71d483" />
     


    
  

