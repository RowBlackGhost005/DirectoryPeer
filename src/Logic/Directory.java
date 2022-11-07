package Logic;

import domain.Peer;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that controls the information about the Peers conected into the network.
 * 
 * @author Luis Angel Marin
 */
public class Directory implements IDirectory{

    private List<Peer> connectedPeers;
    
    private List<Integer> portPool;
    
    private static int peerCounter = 0;
    
    /**
     * Creates a new Peer Diretory with a blank list of Peers conected and a port pool of 10 poorts (from range 4450-4460).
     */
    public Directory(){
        this.connectedPeers = new ArrayList<Peer>();
        this.portPool = new ArrayList<Integer>();

        for (int i = 4450; i <= 4460; i++) {
            addPort(i);
        }

    }
    
    @Override
    public Peer registerPeer(){
        
        String assignedId = "Peer-" + Integer.toString(peerCounter);
        
        Peer peer = new Peer(assignedId, -1);
        
        peer.setName(assignedId);
        
        peer = assignPort(peer);
        
        addPeer(peer);
        
        peerCounter++;
        
        return peer;
    }
    
    @Override
    public void addPeer(Peer peer) {
        if(!this.connectedPeers.contains(peer))
            this.connectedPeers.add(peer);
    }

    @Override
    public void removePeer(int port) {
        this.connectedPeers.remove(new Peer(null ,port));
    }

    @Override
    public Peer getPeer(int port) {
        Peer peerToSearch = new Peer(null, port);
   
        int indexOfPeer = connectedPeers.indexOf(peerToSearch);

        if (indexOfPeer <= 0) {
            return null;
        }

        return this.connectedPeers.get(indexOfPeer);
    }

    @Override
    public List<Peer> getActivePeers() {
        return this.connectedPeers;
    }
    
    @Override
    public Peer assignPort(Peer peer){
        int portToAssign = portPool.get(0);

        if (portToAssign != 0) {
            peer.setPort(portToAssign);
            removePort(portToAssign);
        }
        
        return peer;
    }

    @Override
    public void addPort(int port) {
        if(!this.portPool.contains(port))
            this.portPool.add(port);
    }

    @Override
    public void removePort(int port) {
        this.portPool.remove(portPool.indexOf(port));
    }
    
    public void printPeers(){
        for(Peer peer : connectedPeers){
            System.out.println(peer);
        }
    }
    
    @Override
    public void print(String message){
        System.out.println(message);
    }
    
}
