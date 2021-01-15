Storehouse Backend App
---

- [App home page](https://gb-storehouse.herokuapp.com)
- [Swagger docs](https://gb-storehouse.herokuapp.com/swagger-ui.html)
- [App API base path](https://gb-storehouse.herokuapp.com/api/v1)
- [App info](https://gb-storehouse.herokuapp.com/check/info)
- [App sources](https://github.com/VaolEr/GB-spring-boot-app)

#### Requests examples (dev)
   - [Items controller](src/test/idea-http-client/items.http)
   - [Suppliers controller](src/test/idea-http-client/suppliers.http)
   - [Categories controller](src/test/idea-http-client/categories.http)
   - [Storehouses controller](src/test/idea-http-client/storehouses.http)
   - [Users controller](src/test/idea-http-client/users.http)
   - [Authentication](src/test/idea-http-client/authentication.http)

#### v0.0.11
    JWT authorization url
- POST `/auth/login`
  

    Users available for tests:
-	User `admin@mail.com`	password	`admin`	status `ACTIVE`
-	User `user@mail.com`	password 	`user`	status `ACTIVE`
-	User `root@mail.com`	password 	`root`	status `BANNED`

    Database communications:
- 	`Admin` have `READ` and `WRITE` permissions;
- 	`User` have only `READ` permissions.

    ADMIN/USER
- GET `/users[/{id}]`   
- GET `/users[?name=]`
  

    ADMIN only
- POST `/users`         
- PUT `/users/{id}`     
- DELETE `/users/{id}`  

#### v0.0.10
- GET `/items[?name=][&size=][&page=][&sort=]`

#### v0.0.9
- GET `/storehouses[/{id}]`
- GET `/storehouses[?name=]`
- GET `/storehouses[/{id}/items]`
- POST `/storehouses`
- PUT `/storehouses/{id}`

#### v0.0.8
- Added swagger docs for API

#### v0.0.7
- GET/POST/PUT `/items`: changed items request/response format (`supplier`, `categories` - now are JSON sub-objects)

#### v0.0.6
- GET `/categories[/{id}]`
- GET `/categories[?name=]`
- GET `/categories[/{id}/items]`
- POST `/categories`
- PUT `/categories/{id}`

#### v0.0.5
- GET `/suppliers[/{id}]`
- GET `/suppliers[?name=]`
- GET `/suppliers[/{id}/items]`
- POST `/suppliers`
- PUT `/suppliers/{id}`

#### v0.0.4
- PUT, DELETE `/items/{id}`

#### v0.0.3
- POST `/items`

#### v0.0.2
- GET `/items[/{id}]`
- GET `/items[?name=]`

#### v0.0.1
- Added basic dependencies, start project, add simple tests
