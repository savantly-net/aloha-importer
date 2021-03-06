# Aloha DBF Importer  

A multi-threaded DBF importer with classes that correspond to the Aloha POS table structure.  


### Docker
The easiest way to get started, is to use the docker image, or docker compose file included.  

`docker run savantly/aloha-importer`  
or  
`docker-compose up`  

https://hub.docker.com/r/savantly/aloha-importer

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

Set the AWS region in the application properties -  
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

The example properties would match a single file in the following s3 key list -  

```
gndxfer/100028/20190822/MOD.DBF
gndxfer/100028/20190822/GNDITEM.dbf
gndxfer/100028/20190822/GNDSLSUM.DBF
gndxfer/100028/20190822/CAT.DBF
gndxfer/100028/20190824/MOD.DBF
gndxfer/100028/20190824/GNDITEM.dbf
gndxfer/100028/20190824/GNDSLSUM.DBF
gndxfer/100028/20190824/CAT.DBF
gndxfer/101591/20190822/MOD.DBF
gndxfer/101591/20190822/GNDITEM.dbf
gndxfer/101591/20190822/GNDSLSUM.DBF
gndxfer/101591/20190822/CAT.DBF
gndxfer/101591/20190824/MOD.DBF
gndxfer/101591/20190824/GNDITEM.dbf
gndxfer/101591/20190824/GNDSLSUM.DBF
gndxfer/101591/20190824/CAT.DBF
```

The digester expects the s3 key to include the POS Key and table name, so they may be extracted with REGEX.  

### Properties  


| Property 	| Description 	| Default 	|
|-	|-	|-	|
| aws.s3.digester.keyPatterns |  A list of regular expressions that the S3 object key is matched against. Only matches will be processed. The default pattern matches keys that include a supported table name.	| [See currently supported Aloha tables](./src/main/java/net/savantly/aloha/importer/dbf/AlohaTable.java)	|
| aws.s3.digester.tableNameCapturePattern 	| This regex pattern is applied to the S3 key to create a capture group for the table name  	| ^(.+/)*(.+)\\.(.+)$ 	|
| aws.s3.digester.tableNameCaptureGroup 	| This is the index of the capture group that holds the table name found by regex  	| 2 	|
| aws.s3.digester.posKeyCapturePattern | This regex pattern is applied to the S3 key to create a capture group for the pos key | ^(\\D+/)*(\\d+){1,6}/(.+)\\.(.+)$
| aws.s3.digester.posKeyCaptureGroup | This is the index of the capture group that holds the pos key found by regex | 2 |
| aws.s3.digester.stopOnS3ReadException | Stop processing if there is an exception reading s3 object | true |
| aws.s3.digester.maxPerPage | maximum number of keys/files in each page. Impacts the thread queue. | 50 |
| aws.s3.digester.prefix | prefix the s3 queries to limit the search path |  |
| aws.s3.digester.delimiter | delimiter  used in s3 queries to indicate folder paths |  |
| aws.s3.digester.afterDigestSqlSource | A uri to a resource that contains a SQL script to run after a digest. | classpath:/digest/afterDigest.sql |
| aws.s3.digester.cron | A cron expression to run the digester | -- |
| aws.s3.digester.cronProps.enabled | Enable the dynamic templating of the cron job, otherwise calls digest with default params | false |
| aws.s3.digester.cronProps.prefixTemplate | A prefix template to be used in combination with the {@link #posKeys} list, and the dates determined by {@link #daysBack}<br/>One digest is executed for each posKey and date<br/>{posKey} and {date} are the available variables in a pseudo handlbars template string. | gndxfer/{posKey}/{date}/ |
| aws.digester.cronProps.posKeyListSource | A uri to a resource that contains a list of posKeys [one per line] to insert in the prefix template, used in combination with each date |classpath:/digest/poskeys |
| aws.digester.cronProps.daysBack | The number of days to subtract from the current date, to begin the sync | 1 |
| aws.digester.cronProps.dateFormat | A custom date format to use in the {@link #prefixTemplate} | YYYYMMDD |
| async.corePoolSize | starting size of the thread pool | 2 |
| async.maxPoolSize | maximum size of the thread pool | 2 |
| async.queueCapacity | max queued requests | 100 |
| async.threadNamePrefix | prefix of name for each spawned thread | AlohaImport- |



