# Vacation Planner

## Overview

Vacation Planner is a mobile Android application developed using Java and XML that helps users organize and manage their vacations and excursions. Users can create, view, update, and delete vacation records, manage excursions associated with each vacation, set date-based alerts, share vacation details, and generate vacation reports.

The application uses the Room persistence library as an abstraction layer over a local SQLite database to store data directly on the device. The app works offline and does not require a remote server or external database.

## Features

- **Vacation Management:** Create, save, update, and delete vacation records.
- **Excursion Management:** Add, update, view, and delete excursions associated with saved vacations.
- **Vacation Search:** Search saved vacations by name.
- **Vacation Reports:** Generate reports of saved vacation information.
- **Date Validation:** Ensure vacation end dates do not precede start dates and excursion dates fall within the associated vacation's date range.
- **Date-Based Alerts:** Set alerts for vacation start dates, vacation end dates, and excursion dates.
- **Share Vacation Details:** Share vacation information through supported sharing options, including text and email.
- **Input Validation:** Enforce a 50-character limit on text fields and filter specified special characters.
- **Local Data Storage:** Store vacation and excursion information locally using Room and SQLite.
- **Offline Functionality:** Access and manage data without an internet connection.

## Technology Stack

- **Programming Language:** Java
- **User Interface:** XML
- **Development Environment:** Android Studio
- **Build System:** Gradle
- **Platform:** Android
- **Minimum SDK:** Android 8.0 (API level 26)
- **Target SDK:** Android 16 (API level 36)
- **Database:** SQLite
- **Persistence Library:** Android Room

## Getting Started

### 1\. Clone the Repository

1. Open Android Studio.
2. Select **Clone Repository**.
3. Copy the repository's HTTPS URL from GitLab and paste it into the URL field.
4. Choose a local directory for the project.
5. Open the cloned project and allow Gradle to sync and download the required dependencies.

Alternatively, clone the repository using a terminal:

```
git clone <https://github.com/EB48/Vacation-Planner-App.git>
```

### 2\. Set Up an Android Device or Emulator

**To use an emulator:**

1. Open Device Manager in Android Studio.
2. Create a new Android Virtual Device or select an existing one.
3. Start the emulator.

**To use a physical Android device:**

1. Connect the device to your computer using a USB cable.
2. Enable Developer Options on the device.
3. Enable USB Debugging.
4. Approve the debugging authorization prompt if it appears.

### 3\. Build and Run the Application

1. Open the project in Android Studio.
2. Wait for Gradle synchronization to finish.
3. Select the target emulator or connected device.
4. Click **Run** to build, install, and launch the application.

## Using the Application

### Home Screen

Launch the application and select **Enter** to navigate to the vacation list.

### Managing Vacations

From the vacation list, users can:

- View previously saved vacations.
- Search for a vacation by name.
- Add a new vacation using the green plus button.
- Generate a vacation report using the **Generate Report** button.
- Select a vacation to view or edit its details.

To create a vacation:

1. Select the green plus button.
2. Enter the vacation title and hotel name.
3. Select the vacation start and end dates.
4. Open the menu in the upper-right corner and select **Save**.

The application validates vacation dates and text input before saving.

To update a vacation, select it from the vacation list, modify its details, and save the changes.

To delete a vacation, open its details and select **Delete** from the menu. Confirm the deletion when prompted. A vacation can only be deleted when it has no associated excursions.

### Managing Excursions

Excursions are associated with individual vacations.

To add an excursion:

1. Open a previously saved vacation.
2. Select the green plus button in the excursion section.
3. Enter the excursion name.
4. Select the excursion date.
5. Open the menu and select **Save**.

The excursion date must fall within the associated vacation's start and end dates. The vacation must be saved before an excursion can be added.

To update an excursion, open it from the vacation details page, make the required changes, and save.

To delete an excursion, open its details, select **Delete** from the menu, and confirm the deletion.

### Alerts and Sharing

**Vacation alerts:** Use the **Alert** menu option to configure date-based alerts for a saved vacation. Alerts are associated with the vacation's start and end dates.

**Excursion alerts:** Use the **Alert** menu option on a saved excursion to configure an alert for its scheduled date.

**Sharing:** Select **Share** from the vacation menu to open the available sharing options, such as text messaging or email.

These features require the corresponding vacation or excursion to be saved first.

### Generating Vacation Reports

1. Save the desired vacation information.
2. Return to the vacation list.
3. Select **Generate Report**.
4. Review the information displayed on the Vacation Report page.

## Database and Data Persistence

Vacation Planner uses the Room persistence library to manage a local SQLite database.

The database architecture includes:

- **Entities:** Define the database tables and stored data.
- **Data Access Objects (DAOs):** Provide methods for database operations.
- **Room Database Class:** Connects the entities and DAOs and defines the database configuration.

Room initializes the database through `Room.databaseBuilder()` using the application context. The database is created when the application first accesses it, and no external database configuration is required.

All vacation and excursion data is stored locally on the Android device. The application does not store this information on a remote server or share it externally through its database layer.

### Data Retention

- Application data remains stored locally between normal app sessions.
- Clearing the application's data permanently removes its locally stored information.
- Uninstalling the application removes its local database and associated application data.
- No internet connection is required for core vacation and excursion management.

## Input Validation and Rules

The application enforces the following rules:

- Vacation end dates cannot precede vacation start dates.
- Excursion dates must fall within the associated vacation's date range.
- Text fields have a maximum length of 50 characters.
- The following characters are filtered from text input: `<`, `>`, `"`, `'`, `%`, `;`, `(`, `)`, `&`, `+`, `#`, `$`, and `*`.
- A vacation cannot be deleted while it has associated excursions.
- A vacation must be saved before excursions can be added.
- Alerts and sharing features require the relevant record to be saved first.

## Building a Release APK

To generate a signed APK for installation or distribution:

1. In Android Studio, navigate to **Build \> Generate Signed App Bundle / APK**.
2. Select **APK** and click **Next**.
3. Select an existing keystore or create a new one.
4. Enter the keystore path, key alias, and required passwords.
5. Select the appropriate build variant, such as `release`.
6. Complete the build process to generate the signed APK file.
7. Locate the generated APK using the output path shown by Android Studio.

**Note:** A release APK should use the `release` build variant. The `debug` variant is intended primarily for development and testing.

### Installing the APK

1. Transfer the APK to the Android device.
2. Locate the file using the device's file manager.
3. If prompted, allow installation from the source used to open the APK.
4. Follow the on-screen instructions to complete installation.

## Project Goals

This project demonstrates Android application development using Java and XML, local database persistence with Room and SQLite, CRUD operations, input validation, date handling, and Android features such as notifications and sharing.

It provides a practical example of building an offline-first mobile application for organizing vacation plans and related activities.





