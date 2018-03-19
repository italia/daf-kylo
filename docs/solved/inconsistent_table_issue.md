## Subject of the issue
- The Kylo-ui is down but the pods are running.
- The anomaly is due to an inconsistent state of the DATABASECHANGELOCK table within the Kylo database. In fact, the table is normally empty and is filled during the start-up of Kylo-services to perform upgrades through the liquibase tool.

### My environment
* Google chrome
* Kylo 0.8.4

### Steps to reproduce
Probably due the resizing of the disks of glusterfs some pods synchronism fails and the table go corrupt.

##### Logs
```
Caused by: liquibase.exception.LockException: Could not acquire change log lock.  Currently locked by x.x.x.x since 3/14/18 7:03 PM
        at liquibase.lockservice.StandardLockService.waitForLock(StandardLockService.java:190)
        at liquibase.Liquibase.update(Liquibase.java:196)
        at liquibase.Liquibase.update(Liquibase.java:192)
        at liquibase.integration.spring.SpringLiquibase.performUpdate(SpringLiquibase.java:431)
        at liquibase.integration.spring.SpringLiquibase.afterPropertiesSet(SpringLiquibase.java:388)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1637)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1574)
        ... 29 more
```

### Solution
The recovery procedure was the TRUNCATE of the DATABASECHANGELOCK table (in the kylo database) and the subsequent cleanup and restart of the pod kylo-services.
This anomaly is quite rare and it usually happens when you restart Kylo when MySql has some problems.