# ButtonChallenge

## Introduction
This project is the delivery of the Button Challenge for the position of partner engineer: [link](https://www.usebutton.com/developers/partner-engineer-coding-challenge/)
The social Network used for this project is Facebook.

## Technologies
- Java 8
- Jersey
- Javascript
- HTML
- Apache Tomcat 8
- Facebook API (Graph API)

## Development Environment
- Eclipse JEE Neon

## Installation
Because of a Facebook restriction, the application has been declared only to be "runnable" only on http://localhost:8080/ButtonChallenge/. More domains can be added if necessary.
### Application
- Install Tomcat 8
- Upload the ButtonChallenge.war on [link](https://drive.google.com/file/d/0B3hZNb5IJQp6OU9pMTV5X0FyM00/view?usp=sharing)
- Copy the war under <TOMCAT_INSTALLATION_FOLDER>/webapps/
- Launch Tomcat 8

### Development environment
- Install Eclipse Neon
- Clone the git project in Eclipse and add it to the workspace

## Functionality Implemented
Once Apache launched, please connect locally to [link](http://localhost:8080/ButtonChallenge/) which is the entry point of the application. In order to enter into Application, you have to connect with a Facebook account.
- Facebook Login
- Facebook Logout
- Search all posts
- Search by postid
- Search posts per date
- Search posts from friend
- Search posts from keyword

## Known issues:
- Error messages are not explicit to the user
- On search, we lookup only on the first page return by Facebook, so results are only for the first page and not "all posts"
- Hastag search is not handled. However, the keyword is enough


## API Documentation
A RESTful API is implemented. The path to access it is /ButtonChallenge/rest/fbapi/

### /ping
ping is a simple method which checks if the connection is established to the server:
http://localhost:8080/ButtonChallenge/rest/fbapi/ping
```javascript
Facebook Service Successfully started.. Ping!
```

### /allposts
Retrieve all the posts for the user connected
**Parameters**
- access_token: the facebook access_token retrieved at the login
- http://localhost:8080/ButtonChallenge/rest/fbapi/allposts?access_token=abcd
```javascript
{
  "data": [
    {
      "id": "123456",
      "message": "one message",
      "story": "Someone somewhere",
      "created_time": "2016-09-21T03:04:49+0000",
      "from": {
        "name": "John Doe",
        "id": "654321"
      }
    },
    {
      "id": "234567",
      "message": "Another message",
      "story": "Mike Johnson with John Doe in NYC",
      "created_time": "2016-09-18T09:21:58+0000",
      "from": {
        "name": "Mike Johnson",
        "id": "765432"
      }
    },
  ],
  "paging": {
    "previous": "https://graph.facebook.com/v2.7/123456/feed?fields=id,message,story,created_time,from&format=json&since=1474427089&access_token=abcd&limit=25&__paging_token=enc_AdCADO12A1BgKGObu0LiZA7OhM3lhFkWJeQUZAZCasSwnEB3OaFI5GGkquYByQma9VYeiWmuzXpBhudzNWbZAZBtinmJC&__previous=1",
    "next": "https://graph.facebook.com/v2.7/123456/feed?fields=id,message,story,created_time,from&format=json&access_token=abcd&limit=25&until=1439076515&__paging_token=enc_AdByFaNZBjvWHMwHLR7gg6ynuZACcieW54XKXtR3ZASnZAwsVHvaLP7IsgtXtyhZCwIPpLULe7x7AtDYjhMjRC4liaoxW"
  }
}
```

### /post
Retrieve the particular post from the id passed in parameter
**Parameters**
- access_token: the facebook access_token retrieved at the login
- id: the facebook id of the post
- http://localhost:8080/ButtonChallenge/rest/fbapi/post?access_token=abcd&id=123456
```javascript
{
  "data": [
    {
      "id": "123456",
      "message": "one message",
      "story": "Someone somewhere",
      "created_time": "2016-09-21T03:04:49+0000",
      "from": {
        "name": "John Doe",
        "id": "654321"
      }
    }
  ],
}
```

### /postdate
Retrieve all the posts from the date passed in parameter
**Parameters**
- access_token: the facebook access_token retrieved at the login
- date: date of the post to lookup. Date has to be in YYYYMMDD format
- http://localhost:8080/ButtonChallenge/rest/fbapi/allposts?access_token=abcd&date=20160921
```javascript
{
  "data": [
    {
      "id": "123456",
      "message": "one message",
      "story": "Someone somewhere",
      "created_time": "2016-09-21T03:04:49+0000",
      "from": {
        "name": "John Doe",
        "id": "654321"
      }
    },
    {
      "id": "234567",
      "message": "Another message",
      "story": "Mike Johnson with John Doe in NYC",
      "created_time": "2016-09-21T09:21:58+0000",
      "from": {
        "name": "Mike Johnson",
        "id": "765432"
      }
    },
  ],
}
```

### /postkeyword
Retrieve all the posts that contains the keyword in its message or story
**Parameters**
- access_token: the facebook access_token retrieved at the login
- keyword: keyword to search in all posts
- http://localhost:8080/ButtonChallenge/rest/fbapi/allposts?access_token=abcd&keyword=somewhere
```javascript
{
  "data": [
    {
      "id": "123456",
      "message": "one message",
      "story": "Someone somewhere",
      "created_time": "2016-09-21T03:04:49+0000",
      "from": {
        "name": "John Doe",
        "id": "654321"
      }
    }
  ],
}
```

### /postfriend
Retrieve all the posts from the friend passed in parameter
**Parameters**
- access_token: the facebook access_token retrieved at the login
- friend: friend of the user who has posted a post
- http://localhost:8080/ButtonChallenge/rest/fbapi/allposts?access_token=abcd&friend=John
```javascript
{
  "data": [
    {
      "id": "123456",
      "message": "one message",
      "story": "Someone somewhere",
      "created_time": "2016-09-21T03:04:49+0000",
      "from": {
        "name": "John Doe",
        "id": "654321"
      }
    },
    {
      "id": "234567",
      "message": "Another message",
      "story": "Mike Johnson with John Doe in NYC",
      "created_time": "2016-09-21T09:21:58+0000",
      "from": {
        "name": "Mike Johnson",
        "id": "765432"
      }
    },
  ],
}
```






