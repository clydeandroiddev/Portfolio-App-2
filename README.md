
# Data Loader

**Objective**: Develop a an android application that loads a list of cars from a JSON File and store it in local database. The app should support both LTR (Left-to-Right) and RTL (Right-to-Left) locales to accommodate users from
different language backgrounds.

## Architecture
 **MVVM**:
  - This architecture promotes reusability and readability of code, that greatly simplify the process of creating simple user interfaces.
  - MVVM separates UI logic from data and business logic through ViewModels, promoting loose coupling and testability.
  - Supports dependency injection, which allows for better modularity and easier maintenance.

## Features
- Fetch and display cars from `cars.json`.
- Support LTR (Left-to-Right) and RTL (Right-to-Left)
- Uses Room persistence library for retreiving data locally
- Sorting
- Search supports English and Arabic inputs


## Demo

<img src="https://github.com/clydeandroiddev/jczm-data-loader/blob/main/screenshots/Screen_recording_20240426_030032.gif" width="300" height="650"/>

## User Interface

|     LTR (Left-to-Right)                |        RTL (Right-to-Left)              | 
| -------------------------------------  | --------------------------------------- | 
| <img src='https://github.com/clydeandroiddev/jczm-data-loader/blob/main/screenshots/Screenshot_20240426_030113.png' width="200" height="450" /> | <img src='https://github.com/clydeandroiddev/jczm-data-loader/blob/main/screenshots/Screenshot_20240426_030138.png'  width="200" height="450" />   |


|     English Search                |        Arabic Search              |
| --------------------------------  | --------------------------------- |
| <img src='https://github.com/clydeandroiddev/jczm-data-loader/blob/main/screenshots/Screenshot_20240426_030302.png' width="200" height="450" /> | <img src='https://github.com/clydeandroiddev/jczm-data-loader/blob/main/screenshots/Screenshot_20240426_030242.png' width="200" height="450" />   |



**TechStack**:
- **Programming Language**: Kotlin
- **Dagger Hilt**: Simplifies dependency injection in Android by generating code at compile time.
- **Room**: Android database library that streamlines database operations and object mapping.
- **Coroutines**: Facilitates asynchronous tasks in Kotlin, enhancing code readability.
- **Shared Preferences**: Stores small key-value data for app preferences and settings.
- **Glide**: Fast and efficient image loading library
