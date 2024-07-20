# Ecommerce Product Service

Welcome to the Ecommerce Product Service project. This service is designed to manage products in an ecommerce platform, including product details, categories, payment, and more.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Service](#running-the-service)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The Ecommerce Product Service is a microservice responsible for handling all operations related to products in an ecommerce platform. It provides a RESTful API for creating, reading, updating, and deleting products.

## Features

- Add, update, and delete products
- Retrieve product details
- Manage product categories
- User authentication for auth token

## Requirements

- Java (v17)
- Mysql 
- Maven

## Installation

1. Clone the repository:

    ```sh
    git clone git@github.com:prernaty/Ecommerce.git
    cd productservice
    ```

2. Install the dependencies:

    ```sh
    mvn clean install
    ```

## Configuration


1. Update the `application.properties` file with your configuration details, including the database username and password:

    ```env
    spring.datasource.url=jdbc:<your db url> eg: mysql://localhost:3306/testdb
    spring.datasource.username=<your_username>
    spring.datasource.password=<your_password>
    ```

## Running the Service

1. Start the Mysql service if it's not already running:

    ```sh
    net start mysql
    ```

2. Start the Ecommerce Product Service from main method



   The service will be available at `http://localhost:8080`.

## Usage

Once the service is running, you can use the following endpoints to interact with the product service:

## Product Service Endpoints

Once the service is running, you can use the following endpoints to interact with the product service:

- `GET /products` - Retrieve a list of products
- `GET /products/:id` - Retrieve details of a specific product
- `POST /products` - Create a new product
- `PUT /products/:id` - Update an existing product
- `DELETE /products/:id` - Delete a product

For other controller information like auth controller and payments controller please check the respective classes.

## Contributing

We welcome contributions to the Ecommerce Product Service project. Please read our [Contributing Guidelines](CONTRIBUTING.md) for more information.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

