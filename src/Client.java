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

    public static int menu() {

        int option_teste;

        Scanner input_exit = new Scanner(System.in);
        System.out.println("----------------------------");
        System.out.println("Options:");
        System.out.println("[1] - Request resource"); //adicionar condicional para nao mostrar quando cliente ativo
        System.out.println("[2] - Release resource");
        System.out.println("[3] - Show waiting queue");
        System.out.println("[0] - EXIT");
        System.out.print("Enter option: ");
        option_teste = input_exit.nextInt();

        return option_teste;
    }


    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];

        menu();

        try {
            Registry registry = LocateRegistry.getRegistry(host);
            CoordinatorInterface stub = (CoordinatorInterface) registry.lookup("CoordinatorInterface");

            if (menu()==1) {
                stub.requestResource(clientId);
//            stub.releaseResource();
//            stub.showRequestQueue();
            }else if (menu()==2){
                stub.releaseResource(clientId);
            }else if (menu()==3){
                stub.showRequestQueue();
            }else{
                //menu();
                //sair
            }


        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}
