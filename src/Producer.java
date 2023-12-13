
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.simple.SimpleLoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Producer {


    private static Random rand = new Random();

    private static String IP_BROKER="";

    private static String exchangeName = null;
    private static String routingKey = null;
    static Logger logger=new SimpleLoggerFactory().getLogger("RabbitMQ-Producer");

    public static void main(String[] args) {

        try {

            IP_BROKER = readline("IP VM1:");
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(IP_BROKER); factory.setPort(5672);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            // Send message to exchange


            //exchangeName = readline("Exchange name?");
            routingKey = readline("Routing key?");


            int counter = 0;
            while (true) {

                int rand_int1 = rand.nextInt(100);
                double rand_dub1 = round(rand.nextDouble(), 2);
                double rand_final = rand_dub1+rand_int1;
                String message = counter + " " + routingKey + " " + "$" + rand_final ;
                counter ++;
                Thread.sleep(1000);
                channel.basicPublish("SuperExchange", "routingKey", null, message.getBytes());
                System.out.println(" Message Sent:" + message);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String readline(String msg) {
        Scanner scaninput = new Scanner(System.in);
        System.out.println(msg);
        return scaninput.nextLine();
    }

    public static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}