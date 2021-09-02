package javaSimulator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import io.nats.client.Connection;
import io.nats.client.Nats;
/**
 * Hello world!
 *
 */
public class App 
{
    static int count = 0;

    protected static class NatsSubscriptionRunnable implements Runnable {
        public void run() {
            String server = "nats://localhost";
            Connection nc;
            

            try {
                System.out.println("Connecting to NATS server");
                nc = Nats.connect(server);
                while (true) {
                    count += 1;
                    int temp = new Random().nextInt((30-20)+1) + 20;
                    int speed = new Random().nextInt((30-20)+1) + 20;
                    int velocity = new Random().nextInt((30-20)+1) + 20;
                    int angle = new Random().nextInt((180-20)+1) + 20;
                    String msg = String.format("{Joint Speed: %d, Joint Velocity: %d, Joint Temperature: %d, Joint Angle: %d}", speed, velocity, temp, angle);
                    // String scount = String.valueOf(count);
                    nc.publish("Schaeffler", msg.getBytes(StandardCharsets.UTF_8));
                    System.out.println("msg Sent : "+msg);
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                ((Throwable) e).printStackTrace();
            }
        }
    }
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        NatsSubscriptionRunnable nsr = new NatsSubscriptionRunnable();
        Thread thread = new Thread(nsr);
        thread.start();
    }
}

