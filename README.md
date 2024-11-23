# Spring Boot Keycloak BFF (Backend For Frontend) Project

## Overview

This Spring Boot application serves as a **Backend for Frontend (BFF)**, designed to facilitate OpenID authentication using **Keycloak** (though it can be adapted for use with any OpenID provider). The primary function of this application is to handle user authentication via Keycloak, and once authenticated, generate an app-specific JWT (JSON Web Token) for the user.

The application is built to support **multiple applications** by providing **custom callback URLs** for each. These applications are managed through an **enum**, which allows the system to dynamically handle redirects and authentication for different environments.

### Key Features:
- **OpenID Authentication with Keycloak**: This application integrates seamlessly with Keycloak to authenticate users via OAuth2/OpenID Connect.
- **JWT Generation**: After a successful login via Keycloak, the app generates a custom JWT token, which can be used for further authentication and authorization purposes.
- **Multi-App Support**: The system supports multiple applications, each with its own callback URL. This makes the app highly flexible for various use cases and client configurations.
- **Custom Login Page**: A dynamic login page is used to direct users to the correct Keycloak authorization flow based on the requested application.
- **Keycloak Compatibility**: While this app is configured to work with Keycloak, it is built to support any OpenID Connect-compatible identity provider by adjusting the OAuth2 configuration.

### Technologies:
- **Spring Boot**: Used for rapid application development with a focus on simplicity and ease of use.
- **Keycloak**: Open-source identity and access management solution used for authentication via OpenID Connect.
- **JWT (JSON Web Tokens)**: Used for secure token-based user authentication.
- **Spring Security**: Handles OAuth2 login, session management, and authentication flows.
- **Gradle**: Dependency management and build tool.

## Setting Up and Running Locally

### 1. Running Keycloak in Development Mode

To run Keycloak locally for development, you can use Docker. Keycloak is an open-source identity provider that supports OAuth2/OpenID Connect authentication.

- **Start Keycloak with Docker**:

  Keycloak can be launched using the following Docker command:

  ```bash
  docker run -d \
    -p 8180:8080 \
    -e KEYCLOAK_ADMIN=admin \
    -e KEYCLOAK_ADMIN_PASSWORD=admin \
    --name keycloak \
    jboss/keycloak:latest
  ```

- **Access the Admin Console**:

  After running Keycloak in Docker, open your browser and go to `http://localhost:8180`. Log in with the credentials:
    - Username: `admin`
    - Password: `admin`

  From there, you can create a new **realm** and **client** for your application.

#### Keycloak Realm Setup

To set up Keycloak for your application, you'll need to create a realm and a client with appropriate configurations:

1. **Create a Realm**:
    - In the Keycloak Admin Console, click on **Add Realm**.
    - Provide a name for the realm (e.g., `master` or your own custom name).

2. **Create a Client**:
    - Under the **Clients** section, click on **Create**.
    - Provide a client ID (e.g., `auth-app`), and set the **Root URL** to your application's URL (e.g., `http://localhost:8080`).
    - Set the **Valid Redirect URIs** to match your callback URLs, e.g., `http://localhost:8080/*`.
    - Configure **Client Authentication** to use `Authorization Code Flow` and set the **Access Type** to `confidential` (or `public` depending on your app's security requirements).

3. **Configure OAuth2/OpenID Settings**:
    - Set the `Authorization Code Flow` grant type.
    - Ensure that **Access Tokens** and **ID Tokens** are enabled.
    - Note the **Client Secret** and other necessary information, as these will be required in the application configuration.

### 2. Configuring App-Specific Callback URLs

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

Replace `https://your-app1-callback-url` and `https://your-app2-callback-url` with the actual callback URLs for your applications. These URLs will be used to redirect users after a successful login.

### 3. OAuth2/OpenID Configuration

The application integrates with Keycloak using OAuth2/OpenID Connect, where users authenticate through Keycloak, and the app then generates a custom JWT token upon successful login.

- You can configure the OAuth2 details such as `client-id`, `client-secret`, `authorization-uri`, and `token-uri` within the application properties or YAML configuration file.

- **Important**: The OAuth2 configuration in the Spring Boot application is based on Keycloak as an example. However, this configuration is not exclusive to Keycloak. The system can easily be adapted to work with any OpenID Connect-compliant identity provider. You just need to replace the Keycloak-specific URLs (like the `authorization-uri`, `token-uri`, etc.) with those of the OpenID provider you want to use.

### 4. Running the Application

To run the application:

- **Build and Run**:
  Use Gradle to build and run the application. You can use the following command to start the Spring Boot application:

  ```bash
  ./gradlew bootRun
  ```

- Once the application is running, you can visit `/app-login` with the following query parameters to initiate the login flow:

  ```http
  GET /app-login?appName=APP1
  ```

  Replace `APP1` with the name of the app you want to log in to (e.g., `APP1`, `APP2`). The `appName` parameter ensures the system knows which application-specific callback URL to use for the login process.

- The app will redirect users to Keycloak (or any configured OpenID provider) for authentication, and upon successful login, it will generate a JWT token.

---

*The README was generated with the help of ChatGPT.*

--- 
