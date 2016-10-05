import java.rmi.Remote;

/**
 * Created by leonardo on 05/10/16.
 */
public interface ServerInterface extends Remote{
    // returs true if the resource is available and allocate
    boolean requestResource(String resourceURI, int processId);
    void releaseResource(String resourceURI, int processId);
    String showRequestQueue(String resourceURI);
}
