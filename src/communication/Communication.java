package communication;

import domain.Request;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class that controls the inputs and outputs of the sockets and the connections
 * that come into this Peer.
 *
 * @author Luis Angel Marin
 */
public class Communication {

    private boolean isOn;

    //Must be the same in the Peer subsystem.
    //It represents the port where this communication is listening.
    private int serverPort = 4444;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private String name;

    //Holds all current connections.
    private static ArrayList<ClientSocket> peers = new ArrayList<>();

    private Serializer serializer;

    private ICommHandler commHandler;

    /**
     * Creates a Communication object with the given name and starts the
     * sockets. The port at which this component will listen its 4444 by
     * default.
     *
     * @param name Name of this communication component.
     */
    public Communication(String name) {

        try {
            this.serverSocket = new ServerSocket(serverPort);
            this.name = name;
            this.isOn = true;
            this.serializer = new Serializer();
            this.commHandler = new CommHandler(this);
            startListen();

        } catch (IOException ex) {
            System.out.println("Exception while trying to create Communication channel");
        }
    }

    /**
     * Starts to listen with the server socket in the selected port, all new
     * connections will be handled by this object only serverSocket.
     */
    public void startListen() {

        System.out.println("Directory Peer Online!");
        
        while (isOn) {

            clientSocket = null;

            try {
                //Waits till a connection is requested
                clientSocket = serverSocket.accept();

                handlePeer(clientSocket);

            } catch (IOException e) {
                System.out.println("Error while trying to accept a new Peer");
            }
        }
    }

    /**
     * Handles the connection of the new socket given as parameter. The socket
     * will be wrapped in a ClientSocket object that handles the I/O streams and
     * runs in a individual thread to keep the connection alive.
     *
     * The new socket will be storage in this communication list.
     *
     * @param socket Socket to add.
     * @throws IOException Exception while trying to open the I/O streams.
     */
    public void handlePeer(Socket socket) throws IOException {

        //Creates the streams for I/O between sockets.
        PrintWriter socketSend = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader socketReceived = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //Creates a new socket to establish the connection between Peers.
        ClientSocket newClientSocket = new ClientSocket(clientSocket, socketSend, socketReceived, this);

        peers.add(newClientSocket);
        
        Thread newSocketThread = new Thread(newClientSocket, name);
        
        newSocketThread.start();
    }

    /**
     * Handles the request that got send into an active socket of this
     * communication module. This handler deserializes the request first into a
     * request to send it to the communication handler.
     *
     * @param operation Request to handle.
     */
    public void handleOperation(String operation, ClientSocket peer) {

        Request request = serializer.deSerialize(operation);

        commHandler.handleOperation(request, peer);
    }

    /**
     * Removes the given peer of this communication list.
     *
     * @param peer Peer to remove
     */
    public void removePeer(ClientSocket peer) {
        peers.remove(peer);
        peer.shutdown();
    }

    /**
     * Sends the given request to the given Peer.
     *
     * @param request Request to send.
     * @param peer Peer to receive the request.
     */
    public void send(Request request, ClientSocket peer) {

        String requestSerialized = serializer.Serialize(request);

        ClientSocket peerToSend = null;
        
//        for (ClientSocket socket : peers) {
//            if (socket.equals(peer)) {
//                peerToSend = socket;
//                break;
//            }
//        }

        peer.send(requestSerialized);

        System.out.println("Sending peers. . .");
        System.out.println(requestSerialized);
        //peerToSend.send(requestSerialized);
        System.out.println(peer.clientSocket.getPort());
        
        System.out.println("Sended");
        
        removePeer(peer);
    }

}
