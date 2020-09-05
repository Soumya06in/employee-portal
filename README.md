# employee-portal
A convenient web-application developed using Spring Boot 2.2.0 and H2 database that essentially exposes two REST services - one to register an employee and another to fetch the list of all employees.<br/>
While registering an employee, the Gender can be either MALE or FEMALE and Department can be either IT,HR or FINANCE.<br/>
The listing of employees API is pageable and can be sorted in ascending order by the first name and last name of employees. By default, it fetches the first page with max 10 records sorted by first name.

## Prerequisite softwares to run the application
Git, JDK 1.8, Maven 3.6.3

## Steps to run the application
1. Download the codebase from github repository.
2. Change to directory "employee-portal" 
3. Optional - Change to directory **employee-portal/config** and update the properties in **application-local.properties**, if required. Sensitive properties like username/password are jasypt encrypted and mentioned like **ENC(XXXXXXXXX)**.
4. Change to "employee-portal" directory and run the following command from terminal:
     - 	**mvn clean install** (This will also run the test cases). This should ensure a jar file created in employee-portal/target/**employee-portal-1.0.0.jar**
5. Command to execute the jar as background process from terminal: 
      -   **java -Dspring.config.location=[PATH-TO_CONFIG-DIRECTORY] -Dspring.profiles.active=local -Djasypt.encryptor.password=[JASYPT-ENCRYPTOR-PASSWORD] -Xmx1024M -jar employee-portal-1.0.0.jar > employee-portal.log &**<br/>
         E.g. *PATH-TO-CONFIG-DIRECTORY=/opt/applications/employee-portal/config/* <br/> 
         		 *JASYPT-ENCRYPTOR-PASSWORD=samplestring*
 
## Testing the REST APIs
1. Once the server starts in port 8080 (default), we can start testing the REST APIs. By default all APIs are secured with Basic Authentication and username/password is required to be provided while calling them. 
     -   We need to go to the following link from browser to test the registration and listing of all employee REST APIs - <http://localhost:8080/swagger-ui.html> and provide the *Basic Authentication username & password*.
    -   We need to test the actuator endpoints from POSTMAN: E.g. **GET** <http://localhost:8080/actuator/health> and provide the Basic Authentication username & password while calling the APIs.
    *Note:* Actuator endpoints doesn't show up correctly in Swagger UI and therefore not exposed through the same.