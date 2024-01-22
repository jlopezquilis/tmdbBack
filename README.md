# The Movie Database Backend project
The aim of this project is creating an API which uses the existent one from **The Movie Database** ([see more](https://developer.themoviedb.org/docs/getting-started)).

## How to use it
You can execute the app by this command: ``` java -jar app.jar ```.

---

The API is designed in a way the HTTP requests are made using as a baseURL [localhost:8080]().

So the possible queries are:
1) **Get the Top movies** &rarr; [localhost/8080/movies]()
2) **Get a movie by its name** &rarr; [localhost/8080/movies/{name}]()
3) **Get a movie by its id** &rarr; [localhost/8080/movies/{id}]()

Once you go into one of this links, you will need to authenticate.
The credentials you will need are:
* **Username:** admin
* **Password:** password

## Utilities used
For developing the API, Spring Framework was used.
Some of the utilities used in the project are:
* Spring Security
* Spring Web MVC
* Spring RestTemplate