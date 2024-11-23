
# Task Management System

## Introduction
The **Task Management System** is a RESTful application built with **Spring Boot**. It provides features like task management, user authentication, and role-based access control. This guide will help you set up and run the application locally, including database configuration and initializing sample data using the `data.sql` file.

---

## Usage

### 1. **Clone the Repository**
Clone the repository to your local machine:
```bash
git clone https://github.com/NasserHeshmat/task-management-system.git
cd task-management-system
```

---

### 2. **Configure the Database**
The application uses an Oracle database by default. Follow these steps to set up the database connection:

1. **Update `application.properties`**
   Open the `src/main/resources/application.properties` file and configure the database connection details:
   ```properties
   spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Initialize the Database with `data.sql`**
   - The file `src/main/resources/data.sql` contains seed data for the application.
   - make sure at your first run that the following property is set:
      ```bash
      spring.sql.init.mode=always
      ``` 
   - after that you can make it a:
      ```bash
      spring.sql.init.mode=never
      ```
---

### 3. **Task Notification Scheduler Configuration**
      -The Task Notification Scheduler is responsible for sending email reminders about tasks that are due soon. The time of execution and the recipient email address can be configured through the application.properties file.
   
   **How It Works**
      -The application uses the **task.notification.cron property** to schedule the execution of the task notification job.

      -The **task.notification.email** property determines the recipient email address for the reminders.

      -The scheduler fetches tasks that are due tomorrow and sends an email notification to the configured email address.

   **Example**
   
      -To configure the scheduler to run at 8:30 AM and send notifications to admin@example.com, update your application.properties as follows:
      ```bash
      task.notification.cron=0 30 8 * * ?
      task.notification.email=admin@example.com
      ```



---

### 4. **Build and Run the Application**
1. **Build the project**:
   ```bash
   mvn clean install
   ```

2. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

3. **Test the Application**:
   - Access the application via `http://localhost:8097`.
   - And you can import the postman collection to test the apis 

