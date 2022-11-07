package communication;

import domain.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Class that Serialize request into JSON format and De-Serializes JSONs into
 * Request objects.
 *
 * @author Luis Angel Marin
 */
public class Serializer {

    //Serializer
    private Gson gson;

    /**
     * Creates a new Serializer.
     */
    public Serializer() {
        this.gson = new GsonBuilder().setDateFormat("MM-dd-yyyy").create();
    }

    /**
     * Serialize the request into a String
     *
     * @param request Request to serialzie.
     * @return Request serialized as a String.
     */
    public String Serialize(Request request) {

        String operation = gson.toJson(request, Request.class);

        return operation;
    }

    /**
     * Deserializes the message received.
     *
     * @param requestSerialized Message to deserialize.
     * @return Request that represents the message deserialized.
     */
    public Request deSerialize(String requestSerialized) {

        Request request = gson.fromJson(requestSerialized, Request.class);

        return request;
    }
}
