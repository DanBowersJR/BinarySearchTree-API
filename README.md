# Binary Search Tree Visualizer

A Java Spring Boot API with MySQL and a simple front end for visualizing Binary Search Trees (BSTs).

## Features
* REST API built with Spring Boot
* MySQL database integration using Spring Data JPA & Hibernate
* Entity, repository, service, and controller layers
* Unit tests included (JUnit 5)
* Maven build and dependency management
* Front end with:
  * Number input for BST generation
  * JSON output of the tree structure
  * Canvas-based BST visualization
  * Buttons to reload previous input or clear the tree
  * Matrix-style background animation

## Tech Stack
* Java 17
* Spring Boot
* Spring Data JPA & Hibernate
* MySQL
* Maven
* HTML, CSS, JavaScript (vanilla)

## Running Locally
1. Clone the repo:
   ```bash
   git clone https://github.com/DanBowersJR/BinarySearchTree-API.git
   cd BinarySearchTree-API
   ```
2. Create your own MySQL database (name it anything you want, e.g. `bst_db`).
3. Update `src/main/resources/application.properties` with your database settings:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
4. Build and run:
   ```bash
   mvn spring-boot:run
   ```
5. Open the front end at: `http://localhost:8080/index.html`

## License
This project will be released under the MIT License.
