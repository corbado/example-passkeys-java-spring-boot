# Java Spring Boot Passkey Example

This is a sample implementation of a Java Spring Boot application that offers passkey authentication. For simple passkey-first authentication, the Corbado UI component is used.

Please see the [full blog post](https://www.corbado.com/blog/passkeys-java-spring-boot) to understand the detailed steps needed to integrate passkeys into Java Spring Boot apps.

## File structure

```
├── complete
|   ├── src/main
|   |   ├── java/com/corbado/springboot
|   |   |   ├── FrontendController.java   # Main controller which renders the HTML templates
|   |   |
|   |   └── resources
|   |       ├── application.properties    # Contains the environment variables
|   |       └── templates
|   |           ├── index.html            # Login page with the Corbado UI component
|   |           └── profile.html          # Profile page with user information
|   └── pom.xml                           # Contains info about the project and implementation details
```

## Prerequisites

Please follow the steps in [Getting started](https://docs.corbado.com/overview/getting-started) to create and configure
a project in the [Corbado developer panel](https://app.corbado.com/signin?framework=Java-Spring&technology=passkeys#register).

Paste your Corbado project ID and API secret in the `applications.properties` file.

## Usage

Then, you can run the project locally by executing the following command inside the `/complete` folder:

```bash
./mvnw spring-boot:run
```

You can access the server via http://localhost:8080/
