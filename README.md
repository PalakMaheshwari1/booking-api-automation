#  hotelbookingautomation
## API Automation Framework for Hotel Booking(https://restful-booker.herokuapp.com/) for Payconiq

## Tech stack
- **Maven project**
- **Rest Assured as API automation framework**
- **JUnit5 as test runner framework**
- **surefire as test reporting**

#### Required environment

- Java 11
- Maven 3+


#### Import project (is optional)
- Should be imported as maven project when done from any ide

## Run tests from cli
Commands syntax:
- ** Running the suite **
```
 mvn clean test

```

## Generate Surefire -Site report
- **Generate report after full test run**
```
 mvn site
```


## Run tests and Generate Surefire -Site report
```
 mvn clean test site
```

