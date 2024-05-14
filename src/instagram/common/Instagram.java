package instagram.common;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Instagram extends Remote {
    String sayHello() throws RemoteException;
}
