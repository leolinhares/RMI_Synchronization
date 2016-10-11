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

    // Queue of processes requesting a resource
    Queue<UUID> queue = new LinkedList<UUID>();

    // True if the resource is allocated, False otherwise
    boolean resource = false;

    // UUID of the client currently using the resource
    UUID clientUsingResource = null;

    @Override
    public String requestResource(UUID clientID) throws RemoteException{
        // Check if the client already has the resource
        if (!clientID.equals(clientUsingResource)){
            // Check if the resource is being used
            if (!resource){
                clientUsingResource = clientID;
                resource = true;
                System.out.println("Client " + clientID + " has the resource\n");
                return "Client " + clientID + " has the resource\n";
            }else{
                if (!queue.contains(clientID)){
                    queue.add(clientID);
                    System.out.println("Client " + clientID + " was queued\n");
                    return "Client " + clientID + " was queued\n";
                }else{
                    System.out.println("Client " + clientID + " already queued\n");
                    return "Client " + clientID + " already queued\n";
                }
            }
        }else {
            System.out.println("Client " + clientID + " already has the resource\n");
            return "Client " + clientID + " already has the resource\n";
        }

    }

    @Override
    public String releaseResource(UUID clientID) throws RemoteException{
        if (clientID.equals(clientUsingResource)){
            if (queue.isEmpty()){
                System.out.println("Client " + clientID + " has released the resource\n");
                resource = false;
                clientUsingResource = null;
            }else {
                System.out.println("Client " + clientID + " has released the resource\n");
                resource = false;
                UUID firstClient = (UUID) queue.poll();
                requestResource(firstClient);
            }
            return "Client " + clientID + " has released the resource\n";
        }else{
            System.out.println("Client " + clientID + " does not have the resource to release it\n");
            return "Client " + clientID + " does not have the resource to release it\n";
        }

    }

    @Override
    public String showRequestQueue() throws RemoteException{
        if (queue.isEmpty()){
            return "Waiting queue is empty\n";
        }else{
            String waitingList = "";
            Iterator iterator = queue.iterator();
            while(iterator.hasNext()){
                String element = String.valueOf(iterator.next());
                waitingList += "Client " + element + "\n";
            }
            return waitingList;
        }
    }

    @Override
    public String clientStatus(UUID clientID) throws RemoteException {
        if (clientID.equals(clientUsingResource)){
            return "Client has the resource\n";
        }else if (queue.contains(clientID)){
            return "Client is the waiting queue\n";
        }else{
            return "Client is idle\n";
        }
    }

    @Override
    public void removeClient(UUID clientID) throws RemoteException {
        if (clientID.equals(clientUsingResource)){
            releaseResource(clientID);
        }else {
            if (queue.contains(clientID)){
                queue.remove(clientID);
            }
        }
        System.out.println("Client " + clientID + " exited\n");

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
