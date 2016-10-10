import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * Created by leonardo on 05/10/16.
 */
public interface CoordinatorInterface extends Remote{

    int requestResource(UUID clientID) throws RemoteException;
    boolean releaseResource(UUID clientID) throws RemoteException;
    String showRequestQueue() throws RemoteException;
    int clientStatus(UUID clientID) throws RemoteException;
    void removeClient(UUID clientID) throws RemoteException;

}
