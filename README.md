# cryptobid-backend
Backend of CryptoBid - Cryptocurrency Auction Management System

## Developer Guide

### Prerequisites
* Java 
* Maven
* MySQL

### Setting up the backend
1. Fork and clone the repository
```shell
git clone https://github.com/<your profile name>/cryptobid-backend
```
2. Open the cloned repo, Find the `application.yml.example` file
3. Create a copy of application.yml file
```shell
cp application.yml.example application.yml
```
4. Replace the datasource dummy values with your local mysql server instance credentials  
example:
```yaml
  datasource:
    url: jdbc:mysql://localhost:3306/cryptobid?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8
    username: rootuser
    password: rootpassword
```
5. Install the dependencies
```shell
mvn clean install
```

### Running the backend
You can directly run the application using the following command
```shell
mvn spring-boot:run
```
