# Music-Hub Backend

The Music Hub Backend is a personal project designed to provide users with a single application for various music-related functionalities. Whether you're looking to manage your favorite songs, explore song lyrics, or interact with user profiles, this backend service has you covered.

## Table of Contents

- [Features](#features)
- [Endpoints](#endpoints)
- [Technologies and Tools Used](#technologies-and-tools-used)
- [Database Schema Creation](#database-schema-creation)
- [Future Improvements](#future-improvements)
- [Under Development](#under-development)

## Features

1. **User Management**
    - Register new users.
    - Authenticate users for secure access to their data.
    - Retrieve user profiles by username or ID.

2. **Favorites Management**
    - Create, view, and delete favorites folders.
    - Add and remove songs from favorites folders.
    - Retrieve all songs in a folder.
    - Retrieve all folders and their contents.

3. **Lyrics Retrieval**
    - Search for songs using keywords.
    - Retrieve song lyrics.
    - Cache song lyrics to improve performance.

## Endpoints

The following endpoints are provided by the Music Hub Backend:

- **User Management**
    - `/users/register` (POST): Register a new user.
    - `/users/auth/register` (POST): Authenticate and register a user.
    - `/users/auth/authenticate` (POST): Authenticate a user.
    - `/users/login` (POST): Log in a user.
    - `/users/all` (GET): Get a list of all users.
    - `/users/id/{id}` (GET): Get a user by ID.
    - `/users/username/{username}` (GET): Get a user by username.

- **Favorites Management**
    - `/favorites/id/{folderId}` (GET): Get a favorite folder by ID.
    - `/favorites/username/{username}` (GET): Get favorite folders by username.
    - `/favorites/name` (GET): Get a favorite folder by folder name.
    - `/favorites/new` (POST): Create a new favorite folder.
    - `/favorites/del/{id}` (DELETE): Delete a favorite folder by ID.
    - `/favorites/add` (POST): Add a song to a favorite folder.
    - `/favorites/all/{username}` (GET): Get all folders and their songs for a user.
    - `/favorites/songs/{folderId}` (GET): Get all songs in a folder.

- **Lyrics Retrieval**
    - `/lyrics/search/{keyword}` (GET): Search for songs based on a keyword.
    - `/lyrics/getLyrics/{id}` (GET): Get song lyrics by ID.

## Technologies and Tools Used

### Core Technologies

- Java
- Spring Boot
- Redis
- Unirest
- MySQL
- Lombok
- REST API

### Integration
- [Genius API](https://rapidapi.com/Glavier/api/genius-song-lyrics1 "Genius API"): Integrated the LyricsController with the Genius API to fetch song lyrics and song information.


### Tools Used

- IntelliJ IDEA
- DataGrip
- Maven
- Postman
- Another Redis Desktop Manager
- Slf4j Logging
- Javadoc
- Git

## Database Schema Creation

The SQL scripts are in the [sql-scripts](/sql-scripts) folder. These scripts are used for creating the initial structure of the database tables and defining their relationships.

## Future Improvements

While the Music Hub Backend currently provides essential functionalities, several areas can be greatly improved and extended:

- **Authentication and Security**: Enhance security by implementing token-based authentication for users. Consider adding user roles for better access control.

- **Exception Handling**: Implement robust exception handling to gracefully handle errors and provide informative error messages.

- **User Profile Editing**: Add features for users to reset passwords, change usernames, and perform other profile-related actions.

- **Account Locking**: Implement mechanisms to lock user accounts after a specified number of failed login attempts to enhance security.

- **Additional Functionalities**: Explore adding new features such as playing songs, synchronizing lyrics with songs, and incorporating game-like elements like "Guess the Song."

## Under Development

The Music Hub Backend is an actively developed project. The following are currently being implemented in a [separate branch (`jwt-authentication`)](https://github.com/geybriyel-se/music-app/tree/jwt-authentication):

#### Security Enhancement with JWT Authentication

The purpose is to enhance the security of the application by implementing JSON Web Token (JWT) authentication. This will provide a more robust and secure authentication mechanism for our users.

#### User Roles and Access Control

To provide better access control and user management, user roles will be introduced. This feature will allow defining different roles with varying levels of access permissions within the application.