package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that wraps a socket to run in an individual thread.
 * This class controls the I/O streams of the socket and its basic information.
 * 
 * @author Luis Angel Marin
 */
public class ClientSocket implements Runnable{
    
    //Class socket
    public Socket clientSocket;
    
    PrintWriter socketSend;
    BufferedReader socketReceived;
    Communication server;
    
    private boolean isOn = true;
    
    /**
     * Creates a new ClientSocket, this socket will run in an individual thread and will handle the I/O streams of the given socket.
     * 
     * @param clientSocket The Socket to hold.
     * @param socketSend The stream to send information.
     * @param socketReceived The stream to receive information.
     * @param server Communication object that work as a server to send the information.
     */
    public ClientSocket(Socket clientSocket, PrintWriter socketSend, BufferedReader socketReceived, Communication server) {
        
        this.clientSocket = clientSocket;
        this.socketSend = socketSend;
        this.socketReceived = socketReceived;
        this.server = server;
        runThreads();
    }
    
    /**
     * Creates and run the threads for the input and output.
     */
    public void runThreads() {
        //SendLoop sendThread = new SendLoop(this);
        //ReceivedLoop recievedThread = new ReceivedLoop(this);

        //Thread send = new Thread(sendThread, "Send");
        //Thread received = new Thread(recievedThread, "Received");

        //send.start();
        //received.start();
        
    }
    
    /**
     * Sends the given message throught the socket.
     * @param message Message to send.
     */
    public void send(String message){
        socketSend.println(message);
    }
    
    /**
     * Receive the information that was sended at this socket.
     * @return Message received as String.
     * @throws IOException Exception while trying to read.
     */
    public String recieved() throws IOException{
        return socketReceived.readLine();
    }
    
    public Communication getServer(){
        return this.server;
    }
    
    public boolean isOn(){
        return this.isOn;
    }
    
    public void shutdown(){
        try {
            clientSocket.close();
            socketSend.close();
            socketReceived.close();
            this.isOn = false;
            System.out.println("Shutting down peer");
        } catch (IOException ex) {
            System.out.println("Error while trying to shutdown a Peer");
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (isOn()) {
            try {
                String clientMessage = recieved();
                if (clientMessage != null) {
                    //System.out.println("Peer: " + clientMessage);
                    getServer().handleOperation(clientMessage, this);
                }
            } catch (IOException ex) {
                System.out.println("Error Hilo Received");
            } finally{
                shutdown();
            }
        }
        
    }
}

class SendLoop implements Runnable{

    private ClientSocket socket;
    
    public SendLoop(ClientSocket socket){
        this.socket = socket;
    }
    
    @Override
    public void run() {
        while (socket.isOn()) {
            String ownMessage = null;
            
//            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//            
//            try {
//                ownMessage = reader.readLine();
//            } catch (IOException ex) {
//                Logger.getLogger(SendLoop.class.getName()).log(Level.SEVERE, null, ex);
//            }
            System.out.println("Im on");
            if (ownMessage != null) {
                socket.send(ownMessage);
                System.out.println("YO: " + ownMessage);
            }
        }
    }
}

class ReceivedLoop implements Runnable{

    private ClientSocket socket;
    
    public ReceivedLoop(ClientSocket socket){
        this.socket = socket;
    }
    
    @Override
    public void run() {
        while (socket.isOn()) {
            try {
                String clientMessage = socket.recieved();
                if (clientMessage != null) {
                    //System.out.println("Peer: " + clientMessage);
                    socket.getServer().handleOperation(clientMessage, socket);
                }
            } catch (IOException ex) {
                System.out.println("Error Hilo Received");
            } finally{
                socket.shutdown();
            }
        }
        
    }
}