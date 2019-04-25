package cnos2;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.Scanner;

public class CNOS2 {


    public static void main(String[] args) throws IOException   
    { 
        Scanner scanner = new Scanner(System.in); // Read in user input
        
        System.out.print("Name : "); // Ask for client name
        String name = scanner.nextLine();
        
        System.out.print("Source port : "); //Ask for port number to listen for messages
        int sourcePort = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Destination IP : "); // Ask for IP address of client to send messages to
        String destinationIP = scanner.nextLine();
        
        System.out.print("Destination Port : "); //Ask for destination port to send messages to
        int destinationPort = Integer.parseInt(scanner.nextLine());
        
        Channel channel = new Channel();
        channel.bind(sourcePort);
        channel.start(); // Start Receiving messages
        System.out.println("Started.");
        
        InetSocketAddress address = new InetSocketAddress(destinationIP, destinationPort); // Assigns a new socket address based on port 
                                                                                           // and ip address info provided
        while(true)
        {
            String msg = scanner.nextLine(); // Read in the next line as a message to be sent
            
            if(msg.isEmpty())
                break;
            
            msg = name + " : " + msg;
            
            channel.sendTo(address, msg); // Send message to specified socket address
            System.out.println(msg);
            
        }
        
        scanner.close(); // Stop reading in user input
        channel.stop(); // Close socket connections
        System.out.println("Closed."); 
     
    }
    
}
