# ticket-booking-rec-app

ticket-booking-rec-app is a cinema tickets booking app created as a training.
It's written in Java with Spring framework usage and has embedded H2 database.
Basically it's a backend app, so its usage is via REST. Application has also
Swagger API documentation, available after deployment at its typical
[URL](http://localhost:8080/swagger-ui.html)

Application data structure:
![Application data structure](/images/DatabaseSchema.png)


System is automatically populated with test data (4 persons, 4 films, 3 rooms
and 6 screenings, all defined in src/main/resources/data.sql).

## Installation

Application is built by Gradle. Please install Gradle if you want to build it.

```bash
gradle clean build
```

## Deployment

```bash
gradle bootRun
```

## Install and deploy

There is also a script which builds and deploys whole application in once.
Its name is buildAndDeploy.sh. It's written in bash.

```bash
./buildAndDeploy.sh
```

## Test script

There is a test bash script - using mainly cURL tool. Run it after build and
deploy. All the results of this scripts are listed in test-results directory.
IMPORTANT - This script can be runned once in program lifecycle.

```bash
./test.sh
```

## Problems, ideas and assumptions
- Used only two DTOs and converters to them - more were deemed unnecessary
  content, for purpose of current application, DTOs aren't used
- System has a discount functionality and weekend prices implemented (user can
	provide a discount code via Patch request on /reservations/{id} with JSON
	with id and discount code provided - also included in test script)
- Haven't used the ResponseEntity<> data structure
- Lack of front-end module - tested with [Postman](https://www.postman.com)

## License
[MIT](https://choosealicense.com/licenses/mit/)