package cnos2;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetSocketAddress;
import java.io.IOException;

public class Channel implements Runnable
{
    private DatagramSocket socket;
    private boolean running;
    
    public void bind(int port) throws SocketException // Bind a port for transmission based on user provided input
    {
        socket = new DatagramSocket(port);
    }
    
    public void start() // Starts a thread for receiving/sending
    {
        Thread thread = new Thread(this);
        thread.start();
    }
    public void stop() // Closes the thread
    {
        running = false;
        socket.close();
    }
    
   
        @Override
        public void run()
        {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length); // Create a new datagram for transmission
            
            running = true;
            while(running) // While the program is receiving input, read incoming datagrams and display message received
            {
                try 
                {    
                    socket.receive(packet);
                
                    String msg = new String(buffer, 0, packet.getLength());
                    System.out.println(msg);
                }
                catch (IOException e)
                {
                    break;
                }
            }
        }
        public void sendTo(InetSocketAddress address, String msg) throws IOException // This function converts messages typed into cmd into datagrams for transmission
        {
            byte[] buffer = msg.getBytes();
        
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            packet.setSocketAddress(address);
        
            socket.send(packet);
        }
    
}
