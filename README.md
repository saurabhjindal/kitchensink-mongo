# KitchenSink-Mongo

## Prerequisites
Ensure you have the following installed and configured before running the application:
- **Git**
- **Maven (mvn)**
- **Java 21**
- **MongoDB** (Ensure MongoDB is running and accessible via a valid URL)

---

## Steps to Run the Application

### 1. Clone the Repository
```sh
git clone https://github.com/saurabhjindal/kitchensink-mongo.git
cd kitchensink-mongo
```

### 2. Start MongoDB
Ensure your MongoDB instance is running and accessible via a connection URL.

### 3. Build the Application
```sh
mvn clean install
```

### 4. Start the Application using Maven Spring Boot Plugin
```sh
mvn spring-boot:run
```

---

## Configuration - Properties File
- By default, the application uses `application-dev.properties` for the **development environment**.
- To use a different properties file, you can specify it using the `spring.config.location` parameter:

```sh
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.config.location=file:/path/to/your/application.properties"
```

Alternatively, you can pass it as an environment variable:

```sh
export SPRING_CONFIG_LOCATION=file:/path/to/your/application.properties
mvn spring-boot:run
```

---

## Additional Notes
- Ensure MongoDB is accessible at the specified URL in the properties file.
- If running in a different environment, modify or create a new properties file as needed.

