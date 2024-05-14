package instagram.servidor;

import instagram.common.Instagram;

public class InstagramImpl
        extends java.rmi.server.UnicastRemoteObject
        implements Instagram {
    public InstagramImpl() throws java.rmi.RemoteException {
        super();  //es el constructor de UnicastRemoteObject.
    }
    public String sayHello() throws java.rmi.RemoteException {
        return "Hola, mundo!";
    }
}