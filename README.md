# Aloha DBF Importer


`POST: /api/s3`  
`content-type: application/json`

```
{
	"posKey": 1234567,
	"bucket": "my-s3-data-bucket",
	"fileKey": "/path/to/my.dbf",
	"table": "GNDITEM"
}
```

This code expects that you have AWS credentials set up per:  

http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html  

Set the AWS region in the appliction properties -  
`aws.region=us-east-1`

Or as an env var -  
`AWS_REGION=us-east-1`  

Configure your target DB in application properties or env vars -  
H2 and PostGres drivers are already included.  

```
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```


[Currently supported Aloha tables](./src/main/java/net/savantly/aloha/importer/domain/common/AlohaTable.java)