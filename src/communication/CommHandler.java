package communication;

import domain.Request;
import Logic.Directory;
import Logic.IDirectory;
import domain.Peer;
import java.util.List;

/**
 * Class that controls the operations that were send or received into the Communication component.
 *  
 * @author Luis Angel Marin
 */
public class CommHandler implements ICommHandler{

    private IDirectory directoryPeer;
    
    private Communication communication;
    
    private Serializer serializer;
    
    public CommHandler(Communication communication){
        this.directoryPeer = new Directory();
        this.communication = communication;
        this.serializer = new Serializer();
    }
    
    @Override
    public Peer registerPeer(){
        return directoryPeer.registerPeer();
    }
    
    @Override
    public void addPeer(Peer peer) {
        directoryPeer.addPeer(peer);
    }

    @Override
    public void removePeer(int port) {
        directoryPeer.removePeer(port);
    }

    @Override
    public Peer getPeer(int port) {
        return directoryPeer.getPeer(port);
    }

    @Override
    public List<Peer> getActivePeers() {
        return directoryPeer.getActivePeers();
    }

    @Override
    public void addPort(int port) {
        directoryPeer.addPort(port);
    }

    @Override
    public void removePort(int port) {
        directoryPeer.removePort(port);
    }
    
    public void print(String message){
        directoryPeer.print(message);
    }

    @Override
    public void handleOperation(Request request, ClientSocket peer) {
        
        switch (request.getOperation().toLowerCase()) {
            case "print":
                print(request.getMessage());
                break;
            case "greetings":
                print(request.getMessage());
                sendRequest(request, peer);
                break;
            case "netregister":
                
                Peer registeredPeer = registerPeer();
                
                print("Successfully registed Peer as: " + registeredPeer.getName()+ " in port: " + registeredPeer.getPort());
                
                Request response = new Request();
                response.setOperation("netregistersuccess");
                response.append(registeredPeer, registeredPeer.getClass().getSimpleName());
                
                sendRequest(response, peer);
                break;
                
            case "getactivepeers":
                
                List<Peer> peers = getActivePeers();
                
                response = new Request("registeractivepeers" , "List of peers");
                
                response.append(peers, "peerlist");
                
                print("Response with the active peers");
                
                sendRequest(response, peer);
               
            default:
                break;

        }

    }
    
    @Override
    public void sendRequest(Request request, ClientSocket peer){
        communication.send(request, peer);
    }

    @Override
    public void sendRequest(Request request) {
        
    }
}
