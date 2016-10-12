import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;
import java.util.Scanner;

/**
 * Created by leonardo on 05/10/16.
 */
public class Client {

    // A Global Unique ID for all Clients
    private static UUID clientId = UUID.randomUUID();

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];

        try {
            // Establish connection with the coordinator (Server)
            Registry registry = LocateRegistry.getRegistry(host);
            CoordinatorInterface stub = (CoordinatorInterface) registry.lookup("CoordinatorInterface");

            do{
                int option_teste;

                Scanner input_exit = new Scanner(System.in);
                System.out.println("\n----------------------------");
                System.out.println("\nOptions: \n");
                System.out.println("[1] - Request resource");
                System.out.println("[2] - Release resource");
                System.out.println("[3] - Show waiting queue");
                System.out.println("[4] - Show status");
                System.out.println("[0] - EXIT \n");
                System.out.print("Enter option: ");
                option_teste = input_exit.nextInt();
                System.out.println("");

                if (option_teste==1) {
                    System.out.println(stub.requestResource(clientId));
                }else if (option_teste==2){
                    System.out.println(stub.releaseResource(clientId));
                }else if (option_teste==3){
                    System.out.println("Waiting Queue:");
                    System.out.println(stub.showRequestQueue());
                }else if (option_teste==4){
                    System.out.print("Client Status: ");
                    System.out.println(stub.clientStatus(clientId));
                }else{
                    stub.removeClient(clientId);
                    break;
                }
            }while (true);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}
