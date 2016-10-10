import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * Created by leonardo on 05/10/16.
 */
public interface CoordinatorInterface extends Remote{

    String requestResource(UUID clientID) throws RemoteException;
    String releaseResource(UUID clientID) throws RemoteException;
    String showRequestQueue() throws RemoteException;
    String clientStatus(UUID clientID) throws RemoteException;
    void removeClient(UUID clientID) throws RemoteException;

}
