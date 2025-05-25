# Missing Soldier Tracker

This project is a simple Java command-line application designed to track soldiers. It allows users to register new soldiers, view their statuses, mark soldiers as missing, remove them from missing, and view full details of a specific soldier.

## Project Structure

```
missing-soldier-tracker-java
├── src
│   ├── Main.java
│   └── Soldier.java
├── README.md
```

## Features

- **Register Soldiers**: Input soldier details such as name, soldier ID, rank, unit, latitude, and longitude.
- **View Soldiers**: Display a list of registered soldiers along with their current statuses.
- **Mark Soldier as Missing**: Mark a registered soldier as missing by their ID.
- **Remove Soldier from Missing**: Change a missing soldier's status back to active.
- **View Full Details**: View all details of a specific soldier by their ID.

## Requirements

- Java Development Kit (JDK) 8 or higher
- Any text editor or IDE for Java development

## How to Run the Application

1. Open a terminal and navigate to the `src` directory:
   ```powershell
   cd missing-soldier-tracker-java/src
   ```
2. Compile the Java files:
   ```powershell
   javac Main.java Soldier.java
   ```
3. Run the application:
   ```powershell
   java Main
   ```

## Usage Instructions

- The program will display a menu with options:
  1. Register Soldier
  2. View Soldiers
  3. Mark Soldier as Missing
  4. Remove Soldier from Missing
  5. View Full Details of a Soldier
  6. Exit
- Enter the number for your desired action and follow the prompts.
- Input is validated to prevent crashes on invalid menu choices.

## Future Enhancements

- Integration with a database for persistent storage of soldier data.
- Export/import soldier data to/from files.
- Additional search and reporting features.
