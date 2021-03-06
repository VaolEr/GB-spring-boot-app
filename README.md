Storehouse Backend App
---

- [App home page](https://gb-storehouse.herokuapp.com)
- [PyDjango front client](https://pydjango-geek-stockcontrol.herokuapp.com/)
- [Swagger docs](https://gb-storehouse.herokuapp.com/swagger-ui.html)
- [App API base path](https://gb-storehouse.herokuapp.com/api/v1)
- [App info](https://gb-storehouse.herokuapp.com/check/info)
- [App sources](https://github.com/VaolEr/GB-spring-boot-app)

#### Requests examples (dev)
   - [Authentication](src/test/idea-http-client/authentication.http)
   - [Categories controller](src/test/idea-http-client/categories.http)
   - [Items controller](src/test/idea-http-client/items.http)
   - [Storehouses controller](src/test/idea-http-client/storehouses.http)
   - [Suppliers controller](src/test/idea-http-client/suppliers.http)
   - [Units controller](src/test/idea-http-client/units.http)
   - [Users controller](src/test/idea-http-client/users.http)

#### v0.0.13
- Add `Units` CRUD
    - GET `/units[/{id}]`
    - GET `/units[?name=]`
    - POST `/units`
    - PUT `/units/{id}`

#### v0.0.12
- Add `Units` to DataBase schema and update previously created data
- GET/POST/PUT `/items`: changed items request/response body ( add `unit` as JSON sub-objects )
- GET `/items`: changed response body ( add `"total_qty"` field )

#### v0.0.11
    JWT authorization uri:
- POST `/auth/login`
  

- Users available for tests:

    | Login | Password | Status |
    | :---: | :---: | :---: |
    |`admin@mail.com`|`admin-password`| `ACTIVE`|
    |`user@mail.com`| `user-password`| `ACTIVE`|
    |`root@mail.com`| `root-password`| `BANNED`|

- Database communications:

    | User | Permissions |
    | :---: | :---: |
    |`Admin`|  `READ / WRITE` 
    |`User`|  `READ` |


    ADMIN / USER  availiable  uri's:
- GET `/users[/{id}]`   
- GET `/users[?name=]`
  

    ADMIN only availiable  uri's
- POST `/users`         
- PUT `/users/{id}`     
- DELETE `/users/{id}`  

#### v0.0.10
- Add paging for items:
    - GET `/items[?name=][&size=][&page=][&sort=]`

#### v0.0.9
- Add `Storehouses` CRUD:
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
- Add `Categories` CRUD:
    - GET `/categories[/{id}]`
    - GET `/categories[?name=]`
    - GET `/categories[/{id}/items]`
    - POST `/categories`
    - PUT `/categories/{id}`

#### v0.0.5
- Add `Suppliers` CRUD:
    - GET `/suppliers[/{id}]`
    - GET `/suppliers[?name=]`
    - GET `/suppliers[/{id}/items]`
    - POST `/suppliers`
    - PUT `/suppliers/{id}`

#### v0.0.4
- Add `Items` CRUD methods:
    - PUT, DELETE `/items/{id}`

#### v0.0.3
- Add `Items` CRUD method:
    - POST `/items`

#### v0.0.2
- Add `Items` CRUD methods:
    - GET `/items[/{id}]`
    - GET `/items[?name=]`

#### v0.0.1
- Added basic dependencies, start project, add simple tests
