# KafkaExample
<b>About Kafka:</b><br>
- An Open source, Distributed Streaming platform that helps in developing Event-Driven Applications.<br>
- It relies on Producer-Consumer (or Publisher-Subscriber) approach to deal with streams of data records.<br>
- Has High accuracy in mainting the data records.<br>
- Maintains order of the data record occurrences also.<br>
- Can be used as Messaging Service.<br>
- Can be used as Location tracking.<br>
- Can be used in Data Analytics as Data gathering event.<br>
  <br>
  
## Kafka Setup and Using it programmatically:

1. Download Kafka Binary from this link - https://kafka.apache.org/downloads<br>
   <br>
2. After download gunzip the downloaded file.<br>
   <br>
3. Start the ZooKeeper service<br>

```shell
<KAFKA_FOLDER>/bin/zookeeper-server-start.sh <KAFKA_FOLDER>/config/zookeeper.properties
```
<br>
&emsp;&emsp;&emsp;&emsp;&emsp;<b>ZooKeeper</b> - acts as a centralized service, maintains naming and configuration data, Kafka cluster nodes and topics, partitions etc.<br>
<br>
4. Start the Broker Service<br>

```shell
<KAFKA_FOLDER>/bin/kafka-server-start.sh <KAFKA_FOLDER>/config/server.properties
```
<br>
5. Create a Kafka topic<br>

```shell
   <KAFKA_FOLDER>/bin/katopics.sh --create --topic KafkaExample --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
```
<br>
6. Create a new maven project with following Maven dependencies<br>

```html
<dependencies>
   <dependency>
      <groupId>org.springframework.kafka</groupId>
      <artifactId>spring-kafka</artifactId>
   </dependency>
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
      <version>2.5.5</version>
   </dependency>
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <version>2.5.5</version>
      <scope>test</scope>
   </dependency>
   <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.22</version>
      <scope>provided</scope>
   </dependency>
</dependencies>
```
<br>
7. To publish the message , we need KafkaTemplate. Let us autowire it and use it inside our API.<br>
   
```java
   @Autowired
   KafkaTemplate<String, String> kafkaTemplate;

   private final String topicName = "KafkaTopic";

   @PostMapping("/publish_message_v1/{message}")
   public String publishMessageV1(@PathVariable String message){
       kafkaTemplate.send(topicName, message);
       return "Published V1 Message Successfully!";
   }
```
<br>
8. To consume the message, we need to listen to the topic. We should be able to achieve with the help of @KafkaListener <br>
   
```java
   @KafkaListener(topics = topicName, groupId = "group_id")
   public void consumeMessageV1(String message) {
       System.out.println("Consumed the message : " + message);
   }
```
<br>
9. We need to add @service tag and @EnableKafka for keeping the Subscriber/Consumer listening to the Published message.<br>
   <br>
10. Now we can start the SpringBootApplication with the following command:<br>

```shell
mvn clean install spring-boot:run
```   
<br>
11. Now call the following API and check the logs.<br>
    
```shell
curl --location --request POST 'http://localhost:9909/api/publish_message_v1/PUBLISH_MESSAGE_V1'
```
<br>
12. Now we can see the Message getting posted and consumed. To cross-check, we can run the following scripts in separate terminals, and we can see the message getting Published and Consumed:<br>
&nbsp;&nbsp;&nbsp;&nbsp;* To Check the publishing Message Manually, Execute the following command and type the message:<br>

```shell
<KAFKA_FOLDER>/bin/kafka-console-producer.sh --topic KafkaTopic --broker-list localhost:9092
> PUBLISH_MESSAGE_V1
```
<br>
&nbsp;&nbsp;&nbsp;&nbsp;* To check the published Message Manually, Execute the following command and call the API in step 10:<br>

```shell
<KAFKA_FOLDER>/bin/kafka-console-consumer.sh --topic KafkaTopic --from-beginning --bootstrap-server localhost:9092
```
      
