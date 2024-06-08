# Wallet Online

Wallet Online is a Exercise Spring Boot application that provides basic functionalities for managing a digital wallet. Users can deposit, withdraw, and transfer funds securely. The application also includes standard features such as JWT authentication, secure login and registration, and email token validation.

## Features

- **Deposit Funds**: Users can add money to their wallet.
- **Withdraw Funds**: Users can withdraw money from their wallet.
- **Transfer Funds**: Users can transfer money to other users' wallets.
- **JWT Authentication**: Secure authentication using JSON Web Tokens.
- **User Management**: Secure login and registration for users.
- **Email Validation**: Validate user accounts through email confirmation during the registration process.
- **Token Validation**: Ensure secure transactions and access through token validation.

## Getting Started

1. **Clone the repository**:
    ```sh
    git clone https://github.com/hasanalmunawr/Online-wallet-SpringBoot.git
    cd Wallet-Online
    ```

2. **Configure application properties**:
   Update the `application.yaml` file with your database credentials, email settings, and JWT secret key.

3. **Run the application**:
    ```sh
    ./mvnw spring-boot:run
    ```

4. **Access the application**:
   Open your browser and navigate to `http://localhost:8080` to access the application.

## Prerequisites

- JDK 11 or higher
- Maven
