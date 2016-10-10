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

    private static UUID clientId = UUID.randomUUID();

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];



        try {
            Registry registry = LocateRegistry.getRegistry(host);
            CoordinatorInterface stub = (CoordinatorInterface) registry.lookup("CoordinatorInterface");

            do{
                int option_teste;

                Scanner input_exit = new Scanner(System.in);
                System.out.println("\n\n----------------------------");
                System.out.println("Options:");
                System.out.println("[1] - Request resource"); //adicionar condicional para nao mostrar quando cliente ativo
                System.out.println("[2] - Release resource");
                System.out.println("[3] - Show waiting queue");
                System.out.println("[4] - Show status");
                System.out.println("[0] - EXIT");
                System.out.print("Enter option: ");
                option_teste = input_exit.nextInt();

                if (option_teste==1) {
                    int status = stub.requestResource(clientId);
                    if (status == 0){
                        System.out.println("Access to the Critic Section has been granted to the Client\n");
                    }else if (status == 1){
                        System.out.println("Client is in the waiting queue\n");
                    }else if (status == 2){
                        System.out.println("Client already queued\n");
                    }else {
                        System.out.println("Client already has the resource\n");
                    }
                }else if (option_teste==2){
                    boolean status = stub.releaseResource(clientId);
                    if (status){
                        System.out.println("Client has released the resource\n");
                    }else {
                        System.out.println("Client does not have the resource to release it\n");
                    }
                }else if (option_teste==3){
                    String queue = stub.showRequestQueue();
                    if (queue.isEmpty()){
                        System.out.println("Waiting queue is empty\n");
                    }else {
                        System.out.println(queue);
                    }
                }else if (option_teste==4){
                    int status = stub.clientStatus(clientId);
                    if (status == 0){
                        System.out.println("Client has the resource\n");
                    }else if(status == 1){
                        System.out.println("Client is the waiting queue\n");
                    }else {
                        System.out.println("Client is idle\n");
                    }
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
