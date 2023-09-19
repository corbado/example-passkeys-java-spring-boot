# Complete passkeys integration example for Spring Boot with Corbado

This is a sample implementation of the Corbado web component being integrated into a web application built with Spring.

## File structure

```
├── complete
|   ├── src/main
|   |   ├── java/com/corbado/springboot
|   |   |   ├── FrontendController.java   # Main controller which renders the HTML templates
|   |   |   └── JsonReader.java           # Fetches json from web URLs
|   |   |
|   |   └── resources
|   |       ├── application.properties    # Contains the environment variables
|   |       └── templates
|   |           ├── index.html            # Login page with the webcomponent
|   |           └── profile.html          # Profile page with user information
|   └── pom.xml                           # Contains info about the project and implementation details
```

## Prerequisites

Please follow the steps in [Getting started](https://docs.corbado.com/overview/getting-started) to create and configure
a project in the [Corbado developer panel](https://app.corbado.com/signin#register).

Paste your project ID in the applications.properties file.

## Usage

Then you can run the project locally by executing the following command inside the /complete folder:

```bash
./mvnw spring-boot:run
```
