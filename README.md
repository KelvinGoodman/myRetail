# myRetail
## Prerequisites
* Set JAVA_Home. For more information follow this guide from [Baeldung](https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux)
* Install [Maven](https://maven.apache.org/install.html)
* Install [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
## Running myRetail API
`git clone git@github.com:KelvinGoodman/myRetail.git`  
`./mvnw spring-boot:run`
## Calling myRetail API
`GET http://localhost:8080/product/{id}`  

Produces a response of the form:
```
{
    "id": "int",
    "name": "String",
    "current_price": {
        "value": "BigDecimal",
        "currency_code": "String"
    }
}
```
For example:
```
{
    "id": 13860433,
    "name": "Ice Age: A Mammoth Christmas Special (Blu-ray)",
    "current_price": {
        "value": 356.41,
        "currency_code": "USD"
    }
}
```
## A Few Notes
1. I embedded a DynamoDB Local instance in the application to store pricing information. After the Spring context is
 created, `DynamoDBInitializer.java` creates an instance of DynamoDB running in memory and populates a table with random
 prices for know product ids (I checked several product ids against Red Sky and made a list of some that work). When the 
 application stops the database is destroyed. Obviously this is cannot be a production solution, but for a proof of 
 concept it eliminates the need to store and protect keys and it makes it easy for others to run the application without 
 first standing up a database.