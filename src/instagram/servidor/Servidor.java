package instagram.servidor;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Servidor extends Remote {
    String sayHello() throws RemoteException;
}
