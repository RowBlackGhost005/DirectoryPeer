package Logic;

import domain.Peer;
import java.util.List;

/**
 * Interface that defines the operations of the Directory Peer.
 * 
 * @author Luis Angel Marin
 */
public interface IDirectory {
    
    /**
     * Register a new peer into the Directory Network and return its credentials
     * with a given name and a port assigned by the directory.
     * @return Peer credentials.
     */
    public Peer registerPeer();

    /**
     * Adds a new Peer into the network.
     * @param peer Peer to add.
     */
    public void addPeer(Peer peer);
    
    /**
     * Removes a conected Peer of the network.
     * @param port Peer to remove.
     */
    public void removePeer(int port);
    
    /**
     * Gets a Peer who is conected at the given port.
     * @param port Port to search.
     * @return Peer if conected onto the port, null otherwise.
     */
    public Peer getPeer(int port);
    
    /**
     * Get a list of all Peers conected.
     * @return List of Peers coencted.
     */
    public List<Peer> getActivePeers();
    
    /**
     * Returns a port from the port pool to the given peer.
     * @param peer Peer to asign a port.
     * @return Peer with a port assigned.
     */
    public Peer assignPort(Peer peer);
    
    /**
     * Add the given port into the port pool.
     * @param port Port to add.
     */
    public void addPort(int port);
    
    /**
     * Removes the given port of the port pool.
     * @param port Port to remove.
     */
    public void removePort(int port);
    
    public void print(String message);
}
