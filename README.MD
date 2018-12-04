# Ododmeter rollback

Simple REST service capable of retrieving data for one Vehicle Identification Number (VIN),
analyzing these different data points, and then providing an output view of the result in JSON format.

## Description

The service identifies records that have an "Odometer Rollback." Since an Odometer should
always increase in value over time, an "Odometer Rollback" is defined as an event in which the
Odometer no longer grows in an ascending manner. The service adds a new output property to indicate
the record on which the Odometer rollback occurred. This new property is only on the
record where the rollback event was recorded, all records following a rollback remain
untouched.

### HTTP routes

#### Service History API

Validate service history by VIN
```
GET       /api/v1/vehicle/history/:vin
```

### Launching the application

To compile and run from source code: `mvn clean spring-boot:run`
To run from executable `jar: java -jar <PATH TO EXECUTABLE JAR>`

### Running the tests

`mvn clean test`

### Deployment

Build the executable jar with maven: `mvn package`
Check if there is executable jar in _target_ directory of the project source folder
This executable jar could be launched as standalone application see _Launching the application_ section

### Built with

- Java v.1.8.0_141 (https://www.java.com/)
- Maven v.3.6.0 (https://maven.apache.org/)
- Spring Boot v.2.0.5 (http://projects.spring.io/spring-boot/)

Implemented by Victor Bashurov
