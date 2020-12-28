Storehouse Backend App
---

- App home page: https://gb-storehouse.herokuapp.com
- App API base path: https://gb-storehouse.herokuapp.com/api/v1
- App info: https://gb-storehouse.herokuapp.com/check/info

#### Requests examples
   - [Items controller](src/test/idea-http-client/items.http)
   - [Suppliers controller](src/test/idea-http-client/suppliers.http)
   - [Categories controller](src/test/idea-http-client/categories.http)

#### v0.0.X
-	User `admin@mail.com`	password	`admin`	status `ACTIVE`
-	User `user@mail.com`	password 	`user`	status `ACTIVE` 
-	User `root@mail.com`	password 	`root`	status `BANNED`
-	Database communications:
- 	`Admin` have `READ` and `WRITE` permissions; 
- 	`User` have only `READ` permissions.

#### v0.0.7
- GET/POST/PUT `/items`: changed items request/response format (supplier, categories - now are JSON sub-objects)

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
