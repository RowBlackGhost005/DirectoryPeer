package domain;

/**
 * Class that holds the basic information of a Peer conected into the network.
 * 
 * @author Luis Angel Marin
 */
public class Peer {
    
    private String name;
    private int port;
    
    /**
     * Creates a new register of a Peer with the given id and port.
     * 
     * @param id name of the peer.
     * @param port Port where the peer is.
     */
    public Peer(String name, int port){
        this.name = name;
        this.port = port;
    }

    /**
     * Returns the name of the peer.
     * @return Current name;
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this peer.
     * @param id New ID.
     */
    public void setName(String id) {
        this.name = id;
    }

    /**
     * Return the port where this Peer is listening.
     * @return Current port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the port where this Peer is listening.
     * @param port New port.
     */
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.port;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Peer other = (Peer) obj;
        return this.port == other.port;
    }

    @Override
    public String toString() {
        return "Peer" + " name: " + name + "| port" + port;
    }
    
    
}
