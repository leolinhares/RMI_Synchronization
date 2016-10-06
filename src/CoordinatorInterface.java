import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * Created by leonardo on 05/10/16.
 */
public interface CoordinatorInterface extends Remote{
    // returns true if the resource is available and allocate
    boolean requestResource(UUID processId) throws RemoteException;
    void releaseResource(UUID processId) throws RemoteException;
    String showRequestQueue() throws RemoteException;
}
