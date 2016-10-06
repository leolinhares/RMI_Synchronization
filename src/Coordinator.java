import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by leonardo on 05/10/16.
 */
public class Coordinator implements CoordinatorInterface {

    public Coordinator() throws RemoteException{
        System.out.println("Coordinator Initialized");
    }

    // queue of processes requesting a resource
    Queue queue = new LinkedList();

    // true se ele tiver alocado, false caso contrario.
    boolean resource = false;
    UUID processUsingResource = null;

    @Override
    public boolean requestResource(UUID processId) throws RemoteException{

        if (!resource){
            processUsingResource = processId;
            System.out.println("Client " + processId + " has the resource\n");
            resource = true;
            return true;
        }else{
            queue.add(processId);
            System.out.println("Client " + processId + " was queued\n");
            return false;
        }
    }

    @Override
    public void releaseResource(UUID processId) throws RemoteException{
        if (queue.isEmpty()){
            resource = false;
            processUsingResource = null;
        }else {
            System.out.println("Client " + processId + " has released the resource\n");
            resource = false;
            UUID firstClient = (UUID) queue.poll();
            requestResource(firstClient);
        }
    }

    @Override
    public String showRequestQueue() throws RemoteException{
        String waitingList = "";
        Iterator iterator = queue.iterator();
        while(iterator.hasNext()){
            String element = String.valueOf(iterator.next());
            waitingList += "Client " + element + "\n";
        }
        return waitingList;
    }

    public static void main(String[] args) {
        try{
            Coordinator coordinator = new Coordinator();
            CoordinatorInterface stub = (CoordinatorInterface) UnicastRemoteObject.exportObject(coordinator,0);

            //Binding
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("CoordinatorInterface", stub);

            System.out.println("Coordinator ready\n\nLog:\n");
        }catch (Exception e){
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }

    }





}
