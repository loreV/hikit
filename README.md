![GitHub Logo](importer/src/main/resources/public/assets/logo/hikit_80.png)
# HiKit [![Build Status](https://travis-ci.com/loreV/hikit.svg?token=poHDkeMHMhAtyZHWqhvU&branch=master)](https://travis-ci.com/loreV/hikit)
> A hike planning and discovery RESTFul service that aims to connect natural, cultural and historical data into unique and informed routes.

## Build Setup

### Requirements
- Java 8
- Maven
### Build
```bash
$ mvn clean package -P build-package
```
The executable file is found at `distribution/target/hikit_<version>.jar`
### Run
 ```bash
$ java -jar hikit_<version>.jar
```
* Assumes
  * MongoDB is available with URI: `mongodb://localhost:32769`
  * 'hikit' Db is available and correctly initialized using `init_db.js`
  * Service is available on port `8990`

#### Properties
* Run with property file:
```bash
$ java -jar hikit_<version>.jar -p </path/to/your.properties>
```
* List of properties
  * `web-port` (default `8990`) - port at which the service is available
  * `mongo-uri` (default is `mongodb://localhost:32769`) - uri to db
  * `db` (default is `hikit`) - db in use
