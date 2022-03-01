# TODO

All requested activities shall be included under this file alongside a matching source code //todo comment section.

## [Spring Boot - INTRODUCTION COURSE]

### Scope

This session focus primarily in the following topics:

* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-validation)
* [Spring HATEOAS](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-spring-hateoas)

### Main activities

- ~~LT1-HTTP Rest Clients~~
  [VR1](https://app.pluralsight.com/library/courses/spring-big-picture/table-of-contents)
  [VR2](https://app.pluralsight.com/library/courses/spring-boot-fundamentals/table-of-contents)
  [WR1](https://www.baeldung.com/spring-boot-start#web-and-the-controller)
   - ~~LT1.1-Implement SpaceShipClient using RestTemplate~~ [WR7](https://www.baeldung.com/spring-webclient-resttemplate)
   - ~~LT1.2-Implement SpaceShipClient using WebClient~~ [WR7](https://www.baeldung.com/spring-webclient-resttemplate)
- ~~LT2-Request validation and error handling~~ [VR3](https://app.pluralsight.com/player?course=spring-rest&author=peter-vanrijn&name=f22098f6-6f8a-4d98-a7f4-be46cdb4ecb0&clip=0&mode=live)
   - ~~LT2.1-Include request validation~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
   - ~~LT2.2-Include MethodArgumentNotValidException class handler method map to 400 HTTP status~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
   - ~~LT2.3-Include InvalidResourceStatusException class handler method map to 400 HTTP status~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
   - ~~LT2.4-Include JsonPatchException class handler method map to 400 HTTP status~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
   - ~~LT2.5-Include EntityNotFoundException class handler method map to 404 HTTP status~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
   - ~~LT2.6-Include DataIntegrityViolationException class handler method map to 409 HTTP status~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
   - ~~LT2.7-Include Exception class handler method map to 500 HTTP status~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- ~~LT3-Enhance persistence layer~~ [VR1](https://app.pluralsight.com/library/courses/spring-data-jpa-getting-started/table-of-contents)
   - ~~LT3.1-Include domain model auditing~~ [WR8](https://www.baeldung.com/database-auditing-jpa#spring)
   - ~~LT3.2-Modify save method to be transactional~~ [VR2](https://app.pluralsight.com/library/courses/data-transactions-spring/table-of-contents)
     [WR2](https://www.baeldung.com/transaction-configuration-with-jpa-and-spring)
   - ~~LT3.3-Include request pagination~~ [WR1](https://www.baeldung.com/spring-data-jpa-pagination-sorting)
   - ~~LT3.4-Include example matching~~ [WR5](https://www.baeldung.com/spring-data-query-by-example)
- ~~LT4-Promote API maturity towards Hypermedia Controls~~
  [VR1](https://app.pluralsight.com/library/courses/spring-big-picture/table-of-contents)
  [VR2](https://app.pluralsight.com/library/courses/spring-boot-fundamentals/table-of-contents)
  [WR2](https://spring.io/guides/gs/rest-service/)
   - ~~LT4.1-Modify SpaceMissionController.getSpaceMissions method to return PagedModel response~~ [VR3](https://app.pluralsight.com/player?course=spring-rest&author=peter-vanrijn&name=6fbffaa5-c1eb-4961-81b8-f34ef28cf5f6&clip=0&mode=live)
   - ~~LT4.2-Add selfref link to GetSpaceMissionResponse~~ [WR5](https://www.baeldung.com/spring-hateoas-tutorial)
   - ~~LT4.3-Add spaceship details link to GetSpaceMissionResponse~~ [WR5](https://www.baeldung.com/spring-hateoas-tutorial)
- LT5-Logging capabilities [WR6](https://www.baeldung.com/mdc-in-log4j-2-logback#mdc-in-slf4jlogback)
  - LT5.1-Add logging for inbound request processing
  - LT5.2-Add logging for outbound response result  
  - LT5.3-Add logging for inbound request processing duration
- LT6-Mapped Diagnostic Context [WR6](https://www.baeldung.com/mdc-in-log4j-2-logback#mdc-in-slf4jlogback)
  - LT6.1-Include trace-id MDC entry  
  - LT6.2-Include operation MDC entry
  - LT6.3-Include missing X-Trace-Id response header
  - LT6.4-Include missing Service-Operation response header
- LT7 Actuator enablement [WR7](https://www.baeldung.com/spring-boot-actuators)
- LT9 Configuration Properties for currency conversion [WR5](https://www.baeldung.com/configuration-properties-in-spring-boot) [VR2](https://app.pluralsight.com/player?course=spring-boot-efficient-development-configuration-deployment&author=dustin-schultz&name=spring-boot-efficient-development-configuration-deployment-m3&clip=0&mode=live)
  -LT9.1-Include various sector currencies and application profiles
- LT10 Application testing [WR1](https://www.baeldung.com/spring-boot-testing) [WR2](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html) [WR3](https://www.tutorialspoint.com/spring_boot/spring_boot_unit_test_cases.htm) [VR1](https://app.pluralsight.com/player?course=tdd-spring-junit5&author=steven-haines&name=d8650a91-07c1-4b80-acbb-ee6c5cdf066d&clip=0&mode=live)
  -LT10-1-Add unit test to the service layer
  -LT10-2-Add integration test to the persistence layer
  -LT10-3-Add integration test to the exposure layer

### Side activities

- ~~LT1-HTTP Rest Clients (spacecrew-manager)~~
  [VR1](https://app.pluralsight.com/library/courses/spring-big-picture/table-of-contents)
  [VR2](https://app.pluralsight.com/library/courses/spring-boot-fundamentals/table-of-contents)
  [WR1](https://www.baeldung.com/spring-boot-start#web-and-the-controller)
   - ~~LT1.1-Implement SpaceShipClient using RestTemplate (spacecrew-manager)~~ [WR7](https://www.baeldung.com/spring-webclient-resttemplate)
   - ~~LT1.2-Implement SpaceShipClient using WebClient (spacecrew-manager)~~ [WR7](https://www.baeldung.com/spring-webclient-resttemplate)
- ~~LT2-Request validation and error handling (spacecrew-manager)~~ [VR3](https://app.pluralsight.com/player?course=spring-rest&author=peter-vanrijn&name=f22098f6-6f8a-4d98-a7f4-be46cdb4ecb0&clip=0&mode=live)
   - ~~LT2.1-Include request validation (spacecrew-manager)~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
   - ~~LT2.2-Include MethodArgumentNotValidException class handler method map to 400 HTTP status (spacecrew-manager)~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
   - ~~LT2.3-Include InvalidResourceStatusException class handler method map to 400 HTTP status (spacecrew-manager)~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
   - ~~LT2.4-Include JsonPatchException class handler method map to 400 HTTP status (spacecrew-manager)~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
   - ~~LT2.5-Include EntityNotFoundException class handler method map to 404 HTTP status (spacecrew-manager)~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
   - ~~LT2.6-Include DataIntegrityViolationException class handler method map to 409 HTTP status (spacecrew-manager)~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
   - ~~LT2.7-Include Exception class handler method map to 500 HTTP status (spacecrew-manager)~~ [WR3](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- ~~LT3-Enhance persistence layer (spacecrew-manager)~~ [VR1](https://app.pluralsight.com/library/courses/spring-data-jpa-getting-started/table-of-contents)
   - ~~LT3.1-Include domain model auditing (spacecrew-manager)~~ [WR8](https://www.baeldung.com/database-auditing-jpa#spring)
   - ~~LT3.2-Modify save method to be transactional (spacecrew-manager)~~ [VR2](https://app.pluralsight.com/library/courses/data-transactions-spring/table-of-contents)
     [WR2](https://www.baeldung.com/transaction-configuration-with-jpa-and-spring)
   - ~~LT3.3-Include request pagination (spacecrew-manager)~~ [WR1](https://www.baeldung.com/spring-data-jpa-pagination-sorting)
   - ~~LT3.4-Include example matching (spacecrew-manager)~~ [WR5](https://www.baeldung.com/spring-data-query-by-example)
- ~~LT4-Promote API maturity towards Hypermedia Controls (spacecrew-manager)~~
  [VR1](https://app.pluralsight.com/library/courses/spring-big-picture/table-of-contents)
  [VR2](https://app.pluralsight.com/library/courses/spring-boot-fundamentals/table-of-contents)
  [WR2](https://spring.io/guides/gs/rest-service/)
   - ~~LT4.1-Modify SpaceMissionController.getSpaceMissions method to return PagedModel<GetSpaceMissionResponse> (spacecrew-manager)~~ [VR3](https://app.pluralsight.com/player?course=spring-rest&author=peter-vanrijn&name=6fbffaa5-c1eb-4961-81b8-f34ef28cf5f6&clip=0&mode=live)
   - ~~LT4.2-Add selfref link to GetSpaceMissionResponse (spacecrew-manager)~~ [WR5](https://www.baeldung.com/spring-hateoas-tutorial)
   - ~~LT4.3-Add spaceship details link to GetSpaceMissionResponse (spacecrew-manager)~~ [WR5](https://www.baeldung.com/spring-hateoas-tutorial)