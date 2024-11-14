# API Punto De Pago

API Punto De Pago is a simple SpringBoot application that retrieves weather information for the next 4 days of a city by its name or from a default city from the [Open Weather API](https://openweathermap.org/api).
Made as a technical test for Punto De Pago.

## Table of Contents

- [Technologies](#technologies)
- [Setup](#setup)
- [API Endpoints](#api-endpoints)
- [How To Use](#How-To-Use)

## Technologies

- Java 21.0.5 __(Temurin-21.0.5+11 recommended)__
- Gradle
- Spring Reactive WebClient

## Build & Setup

1. Clone and navigate the repository:
    ```sh
    git clone https://github.com/FatFrogDev/api-punto-de-pago.git
   
    cd api-punto-de-pago
    ```

2. Build the project:
    ```sh
    ./gradlew build
    ```
3. Configure the API Key:
    - Modify the file named `application.yaml` in `src/main/resources/` and add the following line:
    ```properties
        api:
            key: MY_SECRET_API_KEY
    ```

4. Configure CORS (Optional)
   By default is set:
    ``` java
    @CrossOrigin(origins = "http://localhost:4200")
   ```
    You can change the origin in the `APIController.java` file in the `src/main/java/com/fatfrogdev/api/puntodepago/controller` directory if needed.

5. Run the application:
    ``` sh
    ./gradlew bootRun
    ```

## Api Endpoints

> Data is retrieved by OpenWeatherMap API.

1. `Server.port`/api/v1/
- This is the default endpoint. <br>
Gets BOGOTÁ weather information for the next 4 days of request By Default.
2. `Server.port`/api/v1/{city}
- This endpoint tries to get weather information for the next 4 days of a city by th name in the path of the endpoint.

Paris 1 day city response Example:
```json
[
   {"dt":1731668400,"date":"2024-11-15","temp":{"min":6.9,"max":11.22},"weather":[{"description":"cielo claro"}]},
   {"dt":1731754800,"date":"2024-11-16","temp":{"min":6.11,"max":10.75},"weather":[{"description":"cielo claro"}]},
   {"dt":1731841200,"date":"2024-11-17","temp":{"min":6.22,"max":10.18},"weather":[{"description":"lluvia ligera"}]},
   {"dt":1731927600,"date":"2024-11-18","temp":{"min":7.57,"max":10.85},"weather":[{"description":"lluvia ligera"}]}
]
```

## How To Use

You can either run this SpringBott application and consume the endpoints with an HTTP client or you can use [this](https://github.com/FatFrogDev/punto-de-pago-front) frontEnd app made with Angular.

Made with ❤️ by [FatFrogDev](https://github.com/FatFrogDev)
