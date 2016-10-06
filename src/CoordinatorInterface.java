import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * Created by leonardo on 05/10/16.
 */
public interface CoordinatorInterface extends Remote{
    // returns true if the resource is available and allocate
    boolean requestResource(UUID clientID) throws RemoteException;
    void releaseResource(UUID clientID) throws RemoteException;
    String showRequestQueue() throws RemoteException;
    boolean clientStatus(UUID clientID) throws RemoteException;

}
