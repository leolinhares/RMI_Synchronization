import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

/**
 * Created by leonardo on 05/10/16.
 */
public class Client {

    private static UUID clientId = UUID.randomUUID();

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];

        try {
            Registry registry = LocateRegistry.getRegistry(host);
            CoordinatorInterface stub = (CoordinatorInterface) registry.lookup("CoordinatorInterface");

            stub.requestResource(clientId);
//            stub.releaseResource();
//            stub.showRequestQueue();

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
