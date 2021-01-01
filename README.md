#### Build a REST-based JSON mock server to easily add, update, delete, and access data from a JSON file.

> Sample APIs to be supported by the mock server on store.json file:

# 1. `GET`   /posts

### Example : (GET REQUEST) http://localhost:8080/posts 
### Response
```json
{
  "posts": [{"id":0,"title":"best","views":34,"reviews":45,"author":"IO"},
            {"id":1,"title":"title1","views":23,"reviews":34,"author":"JAO"}],
  "status": true,
  "statusCode": 200
}
```

# 2. `GET`   /posts/0

### Example : (GET REQUEST) http://localhost:8080/posts/0
### Response
```json
{
  "posts": {"id":0,"title":"best","views":34,"reviews":45,"author":"IO"},
  "status": true,
  "statusCode": 200
}
```

# 3. `POST` /posts

### Example : (POST REQUEST) http://localhost:8080/posts
### Input Body
```json
{
  "posts":[{"title":"best","views":34,"reviews":45,"author":"IO"},
          {"title":"title1","views":23,"reviews":34,"author":"JAO"}],
  "authors":[{"first_name":"first","last_name":"last","posts":23},
            {"first_name":"first1","last_name":"last1","posts":234}]
}
```
### Response
```json
{
  "posts": [{"id":0,"title":"best","views":34,"reviews":45,"author":"IO"},
            {"id":1,"title":"title1","views":23,"reviews":34,"author":"JAO"}],
  "authors":[{"id":0,"first_name":"first","last_name":"last","posts":23},
              {"id":1,"first_name":"first1","last_name":"last1","posts":234}],
    "status": true,
  "statusCode": 200
}
```

# 4. `PUT` /authors/1
### Example : (PUT REQUEST) http://localhost:8080/authors/1
### Input Body
```json
{
  "first_name":"hello",
  "last_name":"world",
  "posts":456
}
```
### Response
```json
{
  "message": "author updated Successfully",
  "status": true,
  "statusCode": 200
}
```

# 5. `PATCH` /posts/1
### Example : (PATCH REQUEST) http://localhost:8080/posts/0
### Input Body
```json
{
  "title":"hello",
  "author":"world"
}
```
### Response
```json
{
  "message": "post updated Successfully",
  "status": true,
  "statusCode": 200
}
```

# 6. `DELETE` /posts/1
### Example : (DELETE REQUEST) http://localhost:8080/posts/6
### Response
```json
{
  "message": "post succcessfully deleted",
  "status": true,
  "statusCode": 200
}
```


### Enable filtering at entity level :

# 7. `GET` /posts?title=title1&author=CIQ
 
### Enable sorting at entity level :

# 8. `GET` /posts?_sort=views&_order=asc

### Enable basic search at entity level:

# 9. `GET` /posts?q=IQ
