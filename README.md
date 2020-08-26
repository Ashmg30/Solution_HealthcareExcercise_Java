# HealthCare Portal

### Requirements
To Develop a microservice for tracking the status of enrollees in a health care program.
- Enrollees must have an id, name, and activation status (true or false), and a birth date
- Enrollees may have a phone number (although they do not have to supply this)
- Enrollees may have zero or more dependents
- Each of an enrollee's dependents must have an id, name, and birth date

### Implementation
The implementation has been done using [Spring Data Rest](https://spring.io/projects/spring-data-rest) which provides crud APIs over repository which supports all(one-to-one, many-to-one and many-to-many)  relationships. This application uses H2 database with file mode to prevent data failures, however during test run it uses in memory mode for H2 DB.

### Starting Application
To start the application run the following commands 
```
./mvnw clean spring-boot:run
```

The Application will start on `8080` port which you can access via browser at "http://localhost:8080". This will Open HAL browser to access the API listed below.

Following are The API and Sample Payload
`Note` : All Dates are in `yyyy-mm-dd` format
##### To add an Enrollee

```
curl --location --request POST 'localhost:8080/enrollees' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "TestEnrollee",
    "activationStatus" : true,
    "birthDate" :  "1989-11-20",
    "phoneNumber" : 981182821 
}'
```

##### To Modify an Enrollee (Provice EnrolleeId as identifier)

```
curl --location --request PUT 'localhost:8080/enrollees/{enrolleeId}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "UpdatedEnrollee",
    "activationStatus" : true,
    "birthDate" :  "1989-11-20",
    "phoneNumber" : 981182821 
}'
```

##### To Delete and Enrollee
```
curl --location --request DELETE 'localhost:8080/enrollees/{enrolleeId}'
```

##### To Add Dependent to an existing Enrollee (Update EnrolleeId for Enrollee)
```
curl --location --request POST 'localhost:8080/dependents' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "TestDependent",
    "birthDate": "1988-05-22",
    "enrollee":"/enrollees/{enrolleeId}"
}'
```

##### To Remove Dependent from Enrollee(Update EnrolleeId for Enrollee & Updated dependentId with the existing one)
```
curl --location --request DELETE 'localhost:8080/dependents/{dependentId}/enrollee' \
--header 'Content-Type: application/json' \
--data-raw '/enrollees/{enrolleeId}'
```

##### To Modify Exiting Dependent(Updated dependentId with the existing one)
```
curl --location --request PUT 'localhost:8080/dependents/{dependentId}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "UpdatedDependent",
    "birthDate": "1988-05-22",
    "enrollee":"/enrollees/2"
}'
```

##### To Delete an Existing Dependent(Updated dependentId with the existing one)
```
curl --location --request DELETE 'localhost:8080/dependents/{dependentId}'
```

```
In case of error while service is getting up do rm -rf .healthcare.* to delete temporary files
```
