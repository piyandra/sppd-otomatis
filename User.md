# User API Documentation

## Register User

Endpoint: `/api/user/register`
Method: `POST`
### Request Body
```json
{
    "username": "username",
    "password": "password",
    "name": "name"
}
```

### Success Response
```json
{
    "message": "User registered successfully"
}
```
### Error Response
```json
{
    "message": "User already exists"
}
```


## Login User
Endpoint: `/api/user/login`
Method: `GET`

### Request Body
```json
{
    "username": "username",
    "password": "password"
}
```

### Success Response
```json
{
    "message": "User logged in successfully",
    "token" : {
      "token" : "token",
      "expiredAt" : "expiresIn"
  }
}
```

### Error Response Already Logged In
```json
{
    "message": "User already logged in"
}
```

### Error Response Invalid Credentials
```json
{
    "message": "Users or password is incorrect"
}
```

## Logout User
Endpoint: `/api/user/logout`
Method: `GET`

### Response
```json
{
    "message": "User logged out successfully"
}
```

## Get User

Endpoint: `/api/user`
Method: `GET`

### Request
```json
{
    "username": "username"
}
```

### Success Response
```json
{
    "username": "username",
    "name": "name"
}
```


## Update User
Endpoint: `/api/user`
Method: `PUT`

### Request Body
```json
{
    "username": "username",
    "name": "name",
    "password": "password"
}
```

### Success Response
```json
{
    "message": "User updated successfully"
}
```

### Error Response
```json
{
    "message": "User not found"
}
```

## Delete User
Endpoint: `/api/user`
Method: `DELETE`

### Request Body
```json
{
    "username": "username"
}
```

### Success Response
```json
{
    "message": "User deleted successfully"
}
```
### Error Response
```json
{
    "message": "User not found"
}
```