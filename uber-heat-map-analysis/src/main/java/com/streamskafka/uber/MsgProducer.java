/**
 * 
 */
package com.streamskafka.uber;

/**
 * @author prakrish
 *
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
public class MsgProducer {
	 // Declare a new producer
    public static KafkaProducer producer;

    public static void main(String[] args) throws Exception {

        // Set the default stream and topic to publish to.
        String topic = "uberrawdata";
        String fileName = "/home/prakrish/lab3-code/mapr-sparkstreaming-vertx-uberheatmap/src/main/resources/clusters.txt";

        if (args.length == 2) {
            topic = args[0];
            fileName = args[1];

        }
        System.out.println("Sending to topic " + topic);
        configureProducer();
        File f = new File(fileName);
        FileReader fr = new FileReader(f);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        while (line != null) {
  
            /* Add each message to a record. A ProducerRecord object
             identifies the topic or specific partition to publish
             a message to. */
            ProducerRecord<String, String> rec = new ProducerRecord<>(topic,  line);

            // Send the record to the producer client library.
            producer.send(rec);
            System.out.println("Sent message: " + line);
            line = reader.readLine();
             Thread.sleep(600l);

        }

        producer.close();
        reader.close();
        System.out.println("All done.");

        System.exit(1);

    }

    /* Set the value for a configuration parameter.
     This configuration parameter specifies which class
     to use to serialize the value of each message.*/
    public static void configureProducer() {
        Properties props = new Properties();
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(props);
    }

}
