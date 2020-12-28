Storehouse Backend App
---

- [App home page](https://gb-storehouse.herokuapp.com)
- [Swagger docs](https://gb-storehouse.herokuapp.com/swagger-ui/)
- [App API base path](https://gb-storehouse.herokuapp.com/api/v1)
- [App info](https://gb-storehouse.herokuapp.com/check/info)
- [App sources](https://github.com/VaolEr/GB-spring-boot-app)

#### Requests examples (dev)
   - [Items controller](src/test/idea-http-client/items.http)
   - [Suppliers controller](src/test/idea-http-client/suppliers.http)
   - [Categories controller](src/test/idea-http-client/categories.http)

#### v0.0.7
- Added swagger docs

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
