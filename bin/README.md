# backend-tech-assessment

Skeleton project for Backend Technical Assessment.

Includes
--------
- Maven - [pom.xml](pom.xml)
- Application properties - [application.yml](src/main/resources/application.yml)
- Runnable Spring Boot Application - [BackendTechAssessmentApplication](src/main/java/com/intuit/cg/backendtechassessment/BackendTechAssessmentApplication.java)
- REST endpoints - [RequestMappings](src/main/java/com/intuit/cg/backendtechassessment/controller/requestmappings/RequestMappings.java)
- CRUD repositories - [Repository](src/main/java/com/intuit/cg/backendtechassessment/repository/BidRepository.java)
- JPA Entities - [Entities](src/main/java/com/intuit/cg/backendtechassessment/entity/Bid.java)
- Hydrate default data - [DataLoader](src/main/java/com/intuit/cg/backendtechassessment/loader/DataLoader.java)

Requirements
------------
See Backend Technical Assessment document for detailed requirements.

Running Application
------------
1. Run in your favorite IDE:
    - run the following file [BackendTechAssessmentApplication](src/main/java/com/intuit/cg/backendtechassessment/BackendTechAssessmentApplication.java)
2. Run in terminal:
    - in the project root directory, execute the following command: `mvn spring-boot:run`