# Ododmeter rollback

Simple REST service capable of retrieving data for one Vehicle Identification Number (VIN),
analyzing these different data points, and then providing an output view of the result in JSON format.

## Description

The service identifies records that have an "Odometer Rollback." Since an Odometer should
always increase in value over time, an "Odometer Rollback" is defined as an event in which the
Odometer no longer grows in an ascending manner. Please add a new output property to indicate
the record on which the Odometer rollback occurred. This new property should only be on the
record where the rollback event was recorded, all records following a rollback should remain
untouched.

### HTTP routes

#### Service History API

Validate service history by VIN
```
GET       /api/history/:vin
```

### Launching the application

TODO

### Running the tests

TODO

### Deployment

TODO

### Built with

- [Maven] (https://maven.apache.org/)
- [Spring Boot] (http://projects.spring.io/spring-boot/)

Implemented by Victor Bashurov
