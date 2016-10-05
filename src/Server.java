import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by leonardo on 05/10/16.
 */
public class Server implements ServerInterface{

    // It maps a process id with its resource
    Map<Integer, String> resourceMap = new HashMap<Integer, String>();

    // queue of processes requesting a resource
    Queue queue = new LinkedList();


    public static void main(String[] args) {
        try{
            Server server = new Server();
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(server,0);

            //Binding
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ServerInterface", stub);

            System.out.println("Server ready");
        }catch (Exception e){
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }

    }

    @Override
    public boolean requestResource(String resourceURI, int processId) {
        return false;
    }

    @Override
    public void releaseResource(String resourceURI, int processId) {

    }

    @Override
    public String showRequestQueue(String resourceURI) {
        return null;
    }


}
