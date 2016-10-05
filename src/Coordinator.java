import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by leonardo on 05/10/16.
 */
public class Coordinator implements CoordinatorInterface {

    String resource = "../resource/";

    //fazer scan no folder procurando arquivo e monta a tabela de resource

    // It maps a process id with its resource
    Map<Integer, String> resourceMap = new HashMap<Integer, String>();

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
