# Athena Spring Data JDBC test

Test app using Athena JDBC driver with Spring Data Repository

# Building
The project uses gradle 7.5.1 and java 17. If you are using a different version, set `sourceCompatibility` and `targetCompatibility` in build.gradle to the appropriate java versions.  To build, go to the top-level directory and run:
```text
./gradlew build
```

To run the app, go to the top-level directory and run:
```text
./gradlew bootRun
```

There is also an integration test set up to produce the problem (assuming you have Athena set up correctly in the project). To run the unittest:
```text
./gradlew :test --tests "com.bluecedar.service.events.core.LogEventServiceTest.testGetAllResources"
```

### Making a request
The project is also set up with a GET request. To run a request, run:
```text
curl 'http://localhost:8080/v2/logevents?event=WORKFLOW_STARTED'
```

### Spring Data setup

The spring data repository is specified in `com.bluecedar.service.events.domain.LogEventRepository`.

There is a single method in the Repository:
```text
    Page<LogEvent> findAll(Predicate predicate, Pageable pageable);
```

The issue seems to be with the `Pageable` object when it gets down to the Athena Driver level, when there is a `limit` on the pageable. The queries work fine when `Pageable.isUnpaged()` is true. 

To see the code WORKING with unpaged, simply change the last parameter passed to `getAllResources` in `LogEventController.getAll()`. For example, change:
```java
Page<LogEventResponse> page = service.getAllResources(
        "test-resource-owner", predicate, pageable);
```
to
```java
Page<LogEventResponse> page = service.getAllResources(
        "test-resource-owner", predicate, Pageable.unpaged());
```

^ also note that the "resourceownerid" is currently hard-coded to "test-resource-owner", so when setting up data in the athena table, the code would only fetch data for this resourcewonwerid

The same can be done with the provided integration test. There is an `if(true) / else` in `LogEventServiceTest.testGetAllResources`. It is defaulted to WITH paging (failure case). Modify the `true` to `false` for unpaged (working case).

The Entity schema is `com.bluecedar.service.events.domain.LogEvent`. This is exactly what our Athena database looks like. See more details about the database below
##### NOTE: the name of the athena table MUST match the name of the Entity class. So in this case, the Athena table must be named `log_event`

### Athena setup
The connection url is specified in application.yml in `spring.datasource.url` -- Please modify this as necessary. We are using a custom pass-through AWS auth class which uses the AWS v2 SDK instead of the v1 SDK included in the Athena JDBC jar. This is because the v1 AWS SDK does not support SSO auth.

The custom auth chain should work exactly the same for non-SSO related auth. You can set up the ACCESS_KEY and SECRET_KEY as you would normally.

### Athena database
Our Athena database is has partitions for `resourceownerid`, `event`, and `dt` along with partition projections. We are using json files with the following table properties:

```text
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
WITH SERDEPROPERTIES ( 
    'ignore.malformed.json' = 'FALSE', 
    'dots.in.keys' = 'FALSE', 
    'case.insensitive' = 'TRUE', 
    'mapping' = 'TRUE'
)
STORED AS INPUTFORMAT 'org.apache.hadoop.mapred.TextInputFormat' OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
```


