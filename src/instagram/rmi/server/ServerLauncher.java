package instagram.rmi.server;

public class ServerLauncher {
    public static void main(String [ ] args) {
        try {
            javax.rmi.ssl.SslRMIClientSocketFactory rmicsf = new javax.rmi.ssl.SslRMIClientSocketFactory();
            javax.rmi.ssl.SslRMIServerSocketFactory rmissf = new javax.rmi.ssl.SslRMIServerSocketFactory();
            java.rmi.registry.Registry registro =
                    java.rmi.registry.LocateRegistry.createRegistry(1099/*, rmicsf, rmissf*/);
            //registramos el objeto, hablaremos más adelante de re-bind
            InstagramServerImpl oRemoto = new InstagramServerImpl(/*rmicsf, rmissf*/);
            //Accedemos a una referencia al registro (rmiregistry) local
            registro.rebind("ObjetoHello", oRemoto);
            System.err.println("Servidor preparado");
        } catch (Exception e) {
            System.err.println("Excepción del servidor: "+e.toString());
            e.printStackTrace();
        }
    }
}