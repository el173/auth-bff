# Spring Boot OIDC BFF (Backend For Frontend) Project

## Overview

This Spring Boot application serves as a **Backend for Frontend (BFF)**, designed to facilitate OpenID authentication using **Keycloak** (though it can be adapted for use with any OpenID provider). The primary function of this application is to handle user authentication via Keycloak, and once authenticated, generate an app-specific JWT (JSON Web Token) for the user.

The application is built to support **multiple applications** by providing **custom callback URLs** for each. These applications are managed through an **enum**, which allows the system to dynamically handle redirects and authentication for different environments.

### Key Features:
- **OpenID Authentication with Keycloak**: This application integrates seamlessly with Keycloak to authenticate users via OAuth2/OpenID Connect.
- **JWT Generation**: After a successful login via Keycloak, the app generates a custom JWT token, which can be used for further authentication and authorization purposes.
- **Multi-App Support**: The system supports multiple applications, each with its own callback URL. This makes the app highly flexible for various use cases and client configurations.
- **Custom Login Page**: A dynamic login page is used to direct users to the correct Keycloak authorization flow based on the requested application.
- **Keycloak Compatibility**: While this app is configured to work with Keycloak, it is built to support any OpenID Connect-compatible identity provider by adjusting the OAuth2 configuration.
- **Redis Integration**: Uses Redis to store the temporary authorization tokens, ensuring seamless flow and security in login processes.

### Technologies:
- **Spring Boot**: Used for rapid application development with a focus on simplicity and ease of use.
- **Keycloak**: Open-source identity and access management solution used for authentication via OpenID Connect.
- **JWT (JSON Web Tokens)**: Used for secure token-based user authentication.
- **Spring Security**: Handles OAuth2 login, session management, and authentication flows.
- **Gradle**: Dependency management and build tool.
- **Redis**: Acts as an in-memory store.

---

## Setting Up and Running Locally

### 1. Running Keycloak in Development Mode

To run Keycloak locally for development, you can use Docker. Keycloak is an open-source identity provider that supports OAuth2/OpenID Connect authentication.

- **Start Keycloak with Docker**:

  ```bash
  docker run -d \
    -p 8180:8080 \
    -e KEYCLOAK_ADMIN=admin \
    -e KEYCLOAK_ADMIN_PASSWORD=admin \
    --name keycloak \
    jboss/keycloak:latest
  ```

- **Access the Admin Console**:

  Visit `http://localhost:8180` in your browser. Log in with:
    - Username: `admin`
    - Password: `admin`

#### Keycloak Realm Setup

Follow the steps outlined in the original README to create a realm, client, and OAuth2 configurations.

---

### 2. Running Redis with Docker

This application requires Redis to store the OAuth2 state during the login flow. Use the following command to run Redis with authentication enabled:

```bash
docker run -d \
  --name redis-auth \
  -p 6379:6379 \
  redis:latest redis-server --requirepass "yourpassword"
```

---

### 3. Configuring App-Specific Callback URLs

Each application in this system is assigned a unique callback URL. These callback URLs need to be updated in the `application.yml` file before starting the application.

#### Example `application.yml` Configuration:

```yaml
app:
  auth:
    config:
      app1:
        callback: https://your-app1-callback-url
      app2:
        callback: https://your-app2-callback-url
```

Replace `https://your-app1-callback-url` and `https://your-app2-callback-url` with the actual callback URLs for your applications.

---

### 4. Running the Application

To run the application:

- **Build and Run**:
  Use Gradle to build and run the application. Run the following command:

  ```bash
  ./gradlew bootRun
  ```

- Visit `/app-login` with the following query parameters to initiate the login flow:

  ```http
  GET /app-login?appName=APP1
  ```

  Replace `APP1` with the name of the app you want to log in to.

---


*The README was generated with the help of ChatGPT.*

---