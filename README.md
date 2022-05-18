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

## API Endpoints

### Admin Endpoints

|      |                              | Request data             | Response data(JSON)  | Description                      |
|------|------------------------------|--------------------------|----------------------|----------------------------------|
|      | **api/admin/auctions**       |                          |                      |                                  |
| POST | /                            | Auction(body)            | Auction              | Start/Schedule auction           |
| PUT  | /{id}                        | AuctionState(QueryParam) | Auction              | End or Cancel Auction            |
| GET  | /{id}/bids                   | N/A                      | List<Bid>            | Get Bids by Auction Id           |
|      | **api/admin/cryptocurrency** |                          |                      |                                  |
| GET  | /                            | N/A                      | List<Cryptocurrency> | Get all cryptocurrencies         |
| GET  | /{id}                        | N/A                      | Cryptocurrency       | Get Cryptocurrency details by Id |
          
### Default User Endpoints
          
|        |                    | Request data                              | Response data(JSON) | Description                             |
|--------|--------------------|-------------------------------------------|---------------------|-----------------------------------------|
|        | **api/auctions**   |                                           |                     |                                         |
| GET    | /                  | N/A                                       | List<Auction>       | Get all auctions                        |
| GET    | /{id}              | N/A                                       | Auction             | Get Auction by Id                       |
| GET    | /{id}/bids         | UserId (Authentication Header)            | List<Bid>           | Get Logged in user’s bids by Auction Id |
| POST   | /{id}/bids         | Bid(body), UserId (Authentication Header) | Bid                 | Place a bid for auction                 |
| DELETE | /{id}/bids/{bidId} | UserId (Authentication Header)            | N/A                 | Cancel a logged in user’s Bid           |
|        | **api/me**         |                                           |                     |                                         |
| GET    | /                  | N/A                                       | User                | Get logged in user’s details            |
          
### Models with examples

1. Cryptocurrency
```json
          "id": 1,
          "symbol": "BTC",
          "name": "Bitcoin",
          "cryptoRank": 1
```  
          
2. Auction
```json
          "id": 1,
          "startTime": "2022-05-21 21:37:46.000000",
          "endTime": "2022-05-25 21:37:46.000000",
          "initialValue": 1000.10,
          "auctionStatus": "ONGOING",
          "cryptocurrency": { Cryptocurrency }  
```  

3. Bid
```json
          "id": 1,
          "time": "2022-05-21 21:37:46.000000",
          "amount": "500.0",
          "bidBy": { User },
          "auction": { Auction }
```  

4. User
```json
          "id": 1,
          "username": "johndoe",
          "firstName": "John",
          "lastName": "Doe",
          "totalBalance": 5000.4,
          "userType": "DEFAULT"
```
