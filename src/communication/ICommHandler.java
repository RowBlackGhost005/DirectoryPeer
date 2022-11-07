package communication;

import domain.Request;
import domain.Peer;
import java.util.List;

/**
 * Interface that define all the operations that can be done from the
 * Communication module.
 *
 * @author Luis Angel Marin
 */
public interface ICommHandler {

    /**
     * Registers a new peer into the network.
     * @return Returns the credentials of the peer registered.
     */
    public Peer registerPeer();
    
    /**
     * Adds a new Peer into the network.
     *
     * @param peer Peer to add.
     */
    public void addPeer(Peer peer);

    /**
     * Removes a conected Peer of the network.
     *
     * @param port Peer to remove.
     */
    public void removePeer(int port);

    /**
     * Gets a Peer who is conected at the given port.
     *
     * @param port Port to search.
     * @return Peer if conected onto the port, null otherwise.
     */
    public Peer getPeer(int port);

    /**
     * Get a list of all Peers conected.
     *
     * @return List of Peers coencted.
     */
    public List<Peer> getActivePeers();

    /**
     * Add the given port into the port pool.
     *
     * @param port Port to add.
     */
    public void addPort(int port);

    /**
     * Removes the given port of the port pool.
     *
     * @param port Port to remove.
     */
    public void removePort(int port);

    /**
     * Handles the request given.
     *
     * @param request Request to handle.
     * @param peer Peer to be attended.
     */
    public void handleOperation(Request request, ClientSocket peer);

    /**
     * Sends the given request to the given Peer.
     * @param request Request to send.
     */
    public void sendRequest(Request request);
    
    /**
     * Sends the given request to the given peer.
     * @param request Request to send.
     * @param peer Peer to send the request.
     */
    public void sendRequest(Request request, ClientSocket peer);
}
