# Code sharing platform

![Screenshot](img.png "Screenshot")

This is a simple platform to copy, paste, share and receive code anonymously. I created it in order to learn more about Java Spring Boot and MVC. Feel free to use this repo as you like or make contributions.

## Technologies used:
- Apache (WebServer)
- Java (Backend)
- Gradle (Build)
- Spring Boot (API, MVC)
- Bootstrap (CSS)
- Html, Javascript

## How to use:
- **Build**: You can import this project as it is in an IDE (or editor) of your choice and build it with Gradle. I recommend JetBrains IntelliJ IDEA or Eclipse.


- **API**: There are a few endpoints which can be accessed:
  - `/api/code/new` to **post** new code to the server (as **JSON** object e.g. `
    {"code": "print("hello world")"}`)
  - `/api/code/latest` to **get** the most recent uploaded code
  - `/api/code/latest/{id}` to **get** an element by his id (0, 1, 2, etc.)
  - `/api/code/all` to **get** a **JSON** Object with all the posted snippets


### Needs improvement:
- Currently, data gets stored in memory. Improve this by adding a database connector (H2 or PostgreSQL worked best for me)
- Design can be upgraded (I just designed the basics, as I'm more of a backend developer :-))
- Webpage gets rendered serverside. In order to view updates it currently needs to be refreshed (implement some sort of client-side rendering)
