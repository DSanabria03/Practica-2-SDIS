package instagram.servidor;
public class ServidorImpl
        extends java.rmi.server.UnicastRemoteObject
        implements Servidor {
    public ServidorImpl() throws java.rmi.RemoteException {
        super();  //es el constructor de UnicastRemoteObject.
    }
    public String sayHello() throws java.rmi.RemoteException {
        return "Hola, mundo!";
    }
}