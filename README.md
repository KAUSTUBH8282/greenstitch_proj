# Parking Lot Management System

## Overview
This Parking Lot Management System is a Spring Boot-based application designed to facilitate the management of parking lots. It offers a comprehensive suite of features including the ability to add or delete parking slots, park or unpark cars, and query parking slots based on availability, car color, or registration number.

## Features
- **Manage Parking Slots**: Add and delete parking slots within a parking lot.
- **Parking and Unparking**: Support for parking cars in available slots and unparking them as needed.
- **Query Functionality**: Retrieve available slots, find slots by car color or registration number, and more.
- **RESTful API**: Exposes a REST API for interacting with the parking lot system, making it accessible for integration with other systems or applications.

## Technologies Used
- **Spring Boot**: Framework for creating stand-alone, production-grade Spring based Applications.
- **JPA (Java Persistence API)**: For database interaction and management of relational data.
- **MySQL Database**: In-memory database for ease of development and testing.
- **Lombok**: To reduce boilerplate code in Java applications.

## Getting Started

### Prerequisites
- JDK 1.8 or later
- Maven 3.2+
- MySQL Database setup with a user and password

### Setup Instructions
1. Ensure MySQL is installed and running on your system.
2. Create a database for the application in MySQL.
3. Configure the `application.properties` file in the project with your MySQL user and password, as well as the database URL.
4. Install JDK and set it as a PATH variable to ensure command line recognition.
5. Clone the repository:
```bash
git clone https://github.com/KAUSTUBH8282/greenstitch_proj.git

Build the project using Maven:

By Importing pom.xml file after cloning the repository


## Now run the ParkingLotManagementSystemApplication.java file and the application will start and by default, it will be accessible at http://localhost:8080 and test using Postman Preferably and you can also test it using Spring Shell

### API Endpoints(First Run the '/api/parking-slots/add-slots' endpoint to add no of ParkingSlots to a ParkingLot First)
Parking Slots 
GET /api/parking-slots/available: Retrieve available parking slots for a specific parking lot with parkingLotId as Parameter.
POST /api/parking-slots/add-slots: Add parking slots to a parking lot with numberOfSlots as Parameters and get the number of ParkingLot Alloted.
POST /api/parking-slots/delete: Delete a parking lot with parkingLotId as parameter .

Cars
POST /api/parking-slots/park:
Park a car in a parking lot with Car Body as RequestBody and parkingLotId as Parameter
Ex-{
  "registrationNumber": "BKH999",
  "color": "Red"
}.
POST /api/parking-slots/unpark: Unpark a car from a parking slot with parkingLotId and slotNumber as Parameter.
GET /api/parking-slots/cars/registration-numbers: Get registration numbers for cars by color in a specific parking lot with color and parkingLotId as Parameters.
GET /api/parking-slots/cars/slots: Get slot number for a car's registration number in a specific parking lot with registrationNumber and parkingLotId as Parameters.
GET /api/parking-slots/cars/colors/slots: Get slot number for a car's registration number in a specific parking lot with color and parkingLotId as Parameters.

## Spring Shell Integration

The Parking Lot Management System now includes a CLI made possible by Spring Shell, enabling easy interaction through a command-line interface. Below are the available commands and their descriptions:
(First Run the `create-parking-lot` command  to add no of ParkingSlots to a ParkingLot First)
### Built-In Commands
- `help`: Display help about available commands.
- `clear`: Clear the shell screen.
- `quit`, `exit`: Exit the shell.
- `script`: Read and execute commands from a file.

### Parking Lot Commands
- `create-parking-lot`: Create a parking lot with a specified number of slots and return the ID.
- `park-car`: Park a car in the parking lot.
- `unpark-car`: Unpark a car from a slot.
- `get-available-slots-for-parking-lot`: Get available slots for a specific parking lot.
- `get-registration-numbers-for-cars-by-color-in-parking-lot`: Get registration numbers for cars by color in a specific parking lot.
- `get-slot-number-for-registration-number-in-parking-lot`: Get slot number for a car's registration number in a specific parking lot.
- `get-slot-numbers-for-cars-by-color-in-parking-lot`: Get slot numbers for cars by color in a specific parking lot.
- `delete-parking-lot`: Delete a parking lot.

### How to Use Spring Shell Commands
To use the Spring Shell commands, run the application with the CLI profile and enter the shell mode. Here, you can type any of the commands listed above. For example:

- To create a parking lot: `create-parking-lot --numberOfSlots 50`
- To park a car: `park-car --parkingLotId 1 --registrationNumber "ABC123" --color "Blue"`
- To unpark a car: `unpark-car --parkingLotId 1 --slotNumber 5`

For commands that require input, you will be prompted to enter the necessary details. The shell also supports tab completion to quickly fill in command names and options.

### Exiting Spring Shell
To exit the shell, simply type `quit` or `exit`.

By integrating Spring Shell, the system becomes more user-friendly, catering to both programmatic API interactions and manual CLI operations.
