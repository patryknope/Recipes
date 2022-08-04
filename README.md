
# Recipes

Implementation of a "challenging" task from hyperskill.org Java Backend Developer course.

5/5 stages complete.

Technologies used:
- 
- Java

- Spring Boot

- Spring Security

- H2 Database




# Endpoints supported:

- POST /api/register
  receives a JSON object with two fields: email (string), and password (string). If a user with a specified email does not exist, the program saves (registers) the user in a database and responds with 200 (Ok). If a user is already in the database, respond with the 400 (Bad Request) status code. Both fields are required and must be valid: email should contain @ and . symbols, password should contain at least 8 characters and shouldn't be blank. If the fields do not meet these restrictions, the service should respond with 400 (Bad Request)

- POST /api/recipe/new

receives a recipe as a JSON object and returns a JSON object with one id field

- GET /api/recipe/{id}

returns a recipe with a specified id as a JSON object

- DELETE /api/recipe/{id}

deletes a recipe with a specified id

- PUT /api/recipe/{id}

receives a recipe as a JSON object and updates a recipe with a specified id. Also, update the date field too. The server should return the 204 (No Content) status code. If a recipe with a specified id does not exist, the server should return 404 (Not found). The server should respond with 400 (Bad Request) if a recipe doesn't follow the restrictions indicated above (all fields are required, string fields can't be blank, arrays should have at least one item)

- GET /api/recipe/search

takes one of the two mutually exclusive query parameters:

"category" – if this parameter is specified, it returns a JSON array of all recipes of the specified category. Search is case-insensitive, sort the recipes by date (newer first)

"name" – if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter. Search is case-insensitive, sort the recipes by date (newer first).

If no recipes are found, the program should return an empty JSON array. If 0 parameters were passed, or more than 1, the server should return 400 (Bad Request). The same response should follow if the specified parameters are not valid. If everything is correct, it should return 200 (Ok).