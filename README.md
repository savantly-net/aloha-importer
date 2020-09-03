# Aloha DBF Importer  


### Docker Compose
The easiest way to get started, is to use the docker image, or docker compose file included.  

`docker-compose up`  

### Usage  
To import a dbf from S3 -  

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


[See currently supported Aloha tables](./src/main/java/net/savantly/aloha/importer/dbf/AlohaTable.java)


## Bucket Digester
A S3 Bucket digester is available to import all files matching a specific pattern -  

[See default properties](./src/main/java/net/savantly/aloha/importer/aws/AwsConfigProperties.java)  

`POST: /api/s3/digest` will start the digester 


Example Properties -  

```
aws.s3.bucketName=my-s3-bucket-name
aws.s3.digester.enabled=true
aws.s3.digester.keyPatterns[0]=gndxfer/[\d]*/20190822/GNDITEM.dbf
```

### aws.s3.digester.keyPatterns  
A list of regular expressions that the S3 object key is matched against.  
Only matches will be processed.  

### aws.s3.digester.tableNameCapturePattern
This regex pattern is applied to the S3 key to create a capture group for the table name  

default - tableNameCapturePattern = "^(.+/)*(.+)\\.(.+)$"  

### aws.s3.digester.tableNameCaptureGroup
This is the index of the capture group that holds the table name found by regex  

default - tableNameCaptureGroup = 2

### aws.s3.digester.posKeyCapturePattern 
This regex pattern is applied to the S3 key to create a capture group for the pos key   

default - posKeyCapturePattern = "^(\\D+/)*(\\d+){1,6}/(.+)\\.(.+)$"  

### aws.s3.digester.posKeyCaptureGroup
This is the index of the capture group that holds the pos key found by regex  

default - posKeyCaptureGroup = 2

### aws.s3.digester.stopOnS3ReadException
Stop processing if there is an exception reading s3 object  

default - stopOnS3ReadException = true  

