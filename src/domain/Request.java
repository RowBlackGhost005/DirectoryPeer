package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a request send throught sockets. This request should be
 * serialized and de-serialized at both end-points.
 *
 * @author Luis Angel Marin
 */
public class Request {

    //Port of the socket who sends this request.
    private int from;
    //Port of the socket to send this request.
    private int to;

    //Code of the operation to do.
    private String operation;
    //Message to send into the request.
    private String message;

    //Objects that can be send into a request.
    private Peer peer;
    private List<Peer> peers;
    //End objecs that can be send into a request.

    /**
     * Creates an empty request.
     */
    public Request() {
    }

    /**
     * Creates a request that will do the given operation and will have the
     * given message.
     *
     * @param operation Operation to do in the receiver
     * @param message Message of this request.
     */
    public Request(String operation, String message) {
        this.operation = operation;
        this.message = message;
    }

    /**
     * Creates a request that will do the given operation and will have the
     * given message. In addition, the information about who sends and who will
     * receive this request is added into from and to.
     *
     * @param from Who sends this request (port).
     * @param to Who will receive this request (port).
     * @param operation Operation to do in the receiver.
     * @param message Message of this request.
     */
    public Request(int from, int to, String operation, String message) {
        this.from = from;
        this.to = to;
        this.operation = operation;
        this.message = message;
    }

    /**
     * Stores the given object into this request. The type must be provided as
     * the exact name of the Class of the object, this to ensure the receiver
     * will be able to de-seralize this correctly
     *
     * @param object Object to store into the request.
     * @param type Type of the object to store.
     */
    public void append(Object object, String type) {
        
        switch(type.toLowerCase()){
            case "peer":
                this.peer = (Peer) object;
                break;
            case "peerlist":
                this.peers = (ArrayList<Peer>) object;
                break;
            default:
                break;
        }
    }

    /**
     * Return an object stored into this request that has the same Type as the
     * Type given. The type of the object must bhe the exact name of the Class
     * desired.
     *
     * @param type Type of object to retrieve.
     * @return Object of the Class "type", null if isnt stored.
     */
    public Object getParam(String type) {
        
        switch(type.toLowerCase()){
            case "peer":
                return peer;
            case "peerlist":
                return peers;
            default:
                return null;
        }
    }

    /**
     * Gets the code of operation of this request.
     *
     * @return Code of operation.
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Sets the code of operation of this request.
     *
     * @param operation Code of operation.
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * Gets the message of this request.
     *
     * @return Message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message of this request.
     *
     * @param message Message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the port of the socket who send this request.
     *
     * @return Port of sender.
     */
    public int getFrom() {
        return from;
    }

    /**
     * Sets the port of the socket who sends this request.
     *
     * @param from Port of sender.
     */
    public void setFrom(int from) {
        this.from = from;
    }

    /**
     * Gets the port of the socket who will receive this request.
     *
     * @return Receiver.
     */
    public int getTo() {
        return to;
    }

    /**
     * Sets the port of the socket who will receive this request.
     *
     * @param to Receiver.
     */
    public void setTo(int to) {
        this.to = to;
    }

}
