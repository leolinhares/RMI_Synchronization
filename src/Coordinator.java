import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by leonardo on 05/10/16.
 */
public class Coordinator implements CoordinatorInterface {

    // queue of processes requesting a resource
    Queue queue = new LinkedList();


    public static void main(String[] args) {
        try{
            Coordinator coordinator = new Coordinator();
            CoordinatorInterface stub = (CoordinatorInterface) UnicastRemoteObject.exportObject(coordinator,0);

            //Binding
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("CoordinatorInterface", stub);

            System.out.println("Coordinator ready");
        }catch (Exception e){
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }

    }


    @Override
    public boolean requestResource(UUID processId) {
        return false;
    }

    @Override
    public void releaseResource(UUID processId) {

    }

    @Override
    public String showRequestQueue() {
        return null;
    }


}
