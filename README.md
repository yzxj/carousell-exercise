# Diddit | Carousell Code Exercise

Initialized using boilerplate code from the [Getting Started with Gradle on Heroku](https://devcenter.heroku.com/articles/getting-started-with-gradle-on-heroku) article.

The app runs on Java/Ratpack which serves a single-page AngularJS app using the public folder opened by Ratpack. The data structure, which uses two sorted lists (implemented using TreeSet) is accessible via APIs at '/api/[apiname]'.

Demo can be found here: https://yzxj-diddit.herokuapp.com/

## Running Locally

Make sure you have Java installed.  Also, install the [Heroku Toolbelt](https://toolbelt.heroku.com/).

For Linux/Unix/Mac:
```sh
$ git clone https://github.com/heroku/gradle-getting-started.git
$ cd gradle-getting-started
$ ./gradlew stage
$ heroku local web
```
For Windows:
```sh
$ git clone https://github.com/heroku/gradle-getting-started.git
$ cd gradle-getting-started
$ gradlew.bat stage
$ heroku local web -f Procfile.windows
```

Your app should now be running on [localhost:5000](http://localhost:5000/).

## Deploying to Heroku

```sh
$ heroku login
$ heroku create <app-name>
$ git push heroku master
$ heroku open
```

## Reviewing the Code

If you are from Carousell and reviewing my code, these are the files you would want to pay attention to:
```
src/main/java/Main.java
src/main/java/TopicManager.java
src/main/java/Topic.java
src/ratpack/templates/index.html
src/ratpack/public/main.js
```
