# Digg | Carousell Code Exercise

Initialized using boilerplate code from the [Getting Started with Gradle on Heroku](https://devcenter.heroku.com/articles/getting-started-with-gradle-on-heroku) article.

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
