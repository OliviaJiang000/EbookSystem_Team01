CSC8019_team01.  ReadMe



It is a  project run in  the local environment,so you will need to create the database, run file 'DemoApplication.java' ,and open 'homePage.html'.

1. Use  ebookTeam01.sql  to create the database (database name is 'ebook_db' )
2. run file 'DemoApplication.java' . It is in the path  'demo/src/main/java/com/example/demo/DemoApplication.java'
3. open 'homePage.html'.   It is in the path  'demo/src/main/resources/frontend/HomePage/HomePage/HomePage.html'



Also, AddedBookCovers file is to save cover images when add books, please do not delete it.

Below shows the information to login as an admin , 

--- Username: admin

--- Password:12345678


Frame

CSC8019-team1-main/
├── API Design.pdf
├── ReadMe.md
├── ebookTeam01.sql                  # Database schema script
├── .idea/                           # IntelliJ project configuration
│   └── *.xml, .iml, .gitignore
├── .vscode/                         # VSCode editor configuration
│   └── settings.json
├── AddedBookCovers/                 # save cover image of Books added by admin 
├── demo/                            # Main Java Spring Boot project directory
│   ├── pom.xml                      # Maven configuration file
│   ├── .gitattributes / .gitignore
│   ├── .mvn/wrapper/                # Maven wrapper support
│   ├── AddedBookCovers/            # Copy of book cover images
│   └── src/
│       └── main/
│           └── java/
│               └── com/example/demo/
│                   ├── DemoApplication.java
│                   ├── common/                 # Common response body Result
│                   ├── config/                 # CORS and security configuration classes
│                   ├── controller/             # Controllers (REST API)
│                   ├── dto/                    # Data Transfer Objects (DTO)
│                   ├── entity/                 # Entity classes (database models)
│                   ├── enums/                  # Role enums
│                   ├── exception/              # Exception handling
│                   ├── repository/             # JPA data access layer
│                   ├── security/               # JWT utilities and filters
│                   └── service/                # Core service logic layer
