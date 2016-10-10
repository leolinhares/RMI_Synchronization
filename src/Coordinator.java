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
    Queue<UUID> queue = new LinkedList<UUID>();

    // true se ele tiver alocado, false caso contrario.
    boolean resource = false;
    UUID clientUsingResource = null;

    @Override
    public int requestResource(UUID clientID) throws RemoteException{
        if (!clientID.equals(clientUsingResource)){
            if (!resource){
                clientUsingResource = clientID;
                System.out.println("Client " + clientID + " has the resource\n");
                resource = true;
                return 0;
            }else{
                if (!queue.contains(clientID)){
                    queue.add(clientID);
                    System.out.println("Client " + clientID + " was queued\n");
                    return 1;
                }else{
                    System.out.println("Client " + clientID + " already queued\n");
                    return 2;
                }
            }
        }else {
            System.out.println("Client " + clientID + " already has the resource\n");
            return 3;
        }

    }

    @Override
    public boolean releaseResource(UUID clientID) throws RemoteException{
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
            return  true;
        }else{
            return false;
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

    @Override
    public int clientStatus(UUID clientID) throws RemoteException {
        if (clientID.equals(clientUsingResource)){
            return 0;
        }else if (queue.contains(clientID)){
            return 1;
        }else{
            return 2;
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
