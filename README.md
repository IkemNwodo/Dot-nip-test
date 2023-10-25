
# NIP Bank API

This is a simple REST API simulates NIBSS Instant Payments (NIP) transfer operation. The API allows users to create accounts, deposit funds, and transfer funds between accounts.
#### Currently using this custom Account Number generator util [Generator.java](https://github.com/IkemNwodo/Dot-nip-test/blob/develop/src/main/java/com/ikem/dotniptest/util/Generator.java) to generate account numbers for new accounts. Will add ability to use last 10 digits of a user phone number as account number, soon.

## Technologies Used
- Spring Boot 3.0.5
- Java 11
- H2 database
- Spring Data JPA
- Spring Boot Validation
- Apache Maven 3.8.6
- Lombok

## API Endpoints
### Create Account
Creates a new bank account.
- Endpoint: POST /api/v1/account/create
- Request Body:





```json
{
    "firstName": "Ikemefuna",
    "lastName": "Nwodo",
    "bvn": "12345678902",
    "password": "1234567890",
    "emailAddress": "nwodofrank@gmail.com"
}
```

- Response Body:

```json
{
    "accountNumber": "0000000001",
    "accountName": "IkemefunaNwodo",
    "balance": 0.0,
    "transactions": []
}
```
### Deposit
Deposits funds into bank account.
- Endpoint: POST  /api/v1/account/transaction/deposit
- Request Body:
```json
{
    "amount": 100,
    "accountNumber": "0000000001"
}
```
- Response Body:

```json
{
    "amount": 100,
    "accountNumber": "0000000001",
    "currentBalance": 100.00,
    "message": "Deposit successful"
}
```

### Transfer
Transfers funds between two bank accounts.
- Endpoint: POST  /api/v1/account/transaction/transfer
- Request Body:
```json
{
    "amount": 100,
    "sourceAccountNumber": "0000000001",
    "destinationAccountNumber": "0000000002"
}
```
- Response Body:

```json
{
    "amount": 100,
    "sourceAccountNumber": "0000000001",
    "destinationAccountNumber": "0000000002"
    "currentBalance": 0.00,
    "message": "Transfer successful"
}
```

### Transactions
Fetch transactions with optional parameters.
- Endpoint: GET /api/v1/account/transaction?status=completed&accountNumber=123456789&startDate=2023-10-25&endDate=2023-10-30

- Request Body:
```json
{
  
}
```
- Response Body:

```json
{
    
}
```

### Transaction Summary
Fetch transaction summary for a specific date.
- Endpoint: GET /api/v1/account/transaction/summary?date=2023-10-25

- Request Body:
```json
{
  
}
```
- Response Body:

```json
{
    
}
```