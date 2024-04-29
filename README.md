 
# Last Visited

Last Visited is a simple web app that allows users to keep track of websites they want to remember to visit. It records the number of times a user has visited a particular website and the last time they visited it.

## Features

- Add new websites to the list organized by category
- View the list of websites and their visit counts
- See the last time a website was visited

## Technologies Used

### Backend

- Java 
- Spring
  - Boot
  - Web
  - Data JPA
- MySQL
- Maven
- Log4j2
- JUnit 5

### Frontend

- Vanilla JavaScript
- HTML
- CSS

## Installation

1. Clone the repository:

```
git clone https://github.com/miguel-magallanes/last-visited.git
```

2. Navigate to the project directory:

```
cd last-visited
```

3. Configure the MySQL database connection in the `application.properties` file.

4. Build the project using Maven:

```
mvn clean install
```

5. Run the Spring Boot application:

```
mvn spring-boot:run
```

6. Open your web browser and navigate to `http://localhost:8080` to access the application.

## Usage

1. On the web page, you'll see a list of categories and links you've added along with their visit counts and date and time of most recent visit.
2. To add a new link, enter a category, link and URL in the provided input fields.
3. The website will be added to the list, and the visit count will be initialized to 0.
4. Each time you visit a website, the visit count and date/time will be incremented.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.

## License

This project is licensed under the [AGPLv3](https://www.gnu.org/licenses/agpl-3.0.en.html).
