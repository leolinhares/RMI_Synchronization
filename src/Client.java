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
                System.out.println("----------------------------");
                System.out.println("Options:");
                System.out.println("[1] - Request resource"); //adicionar condicional para nao mostrar quando cliente ativo
                System.out.println("[2] - Release resource");
                System.out.println("[3] - Show waiting queue");
                System.out.println("[4] - Show status");
                System.out.println("[0] - EXIT");
                System.out.print("Enter option: ");
                option_teste = input_exit.nextInt();

                if (option_teste==1) {
                    boolean status = stub.requestResource(clientId);
                    if (status){
                        System.out.println("Access to the Critic Section has been granted to the Client\n");
                    }else{
                        System.out.println("Client is in the waiting queue\n");
                    }
                }else if (option_teste==2){
                    stub.releaseResource(clientId);
                    System.out.println("Client has released the resource\n");
                }else if (option_teste==3){
                    System.out.println(stub.showRequestQueue());
                }else if (option_teste==4){
                    boolean status = stub.clientStatus(clientId);
                    if (status){
                        System.out.println("Client has the resource\n");
                    }else {
                        System.out.println("Client is the waiting queue\n");
                    }
                }else{
                    if (stub.clientStatus(clientId)){
                        stub.releaseResource(clientId);
                    }
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
