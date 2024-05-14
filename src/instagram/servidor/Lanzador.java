package instagram.servidor;

public class Lanzador {
    public static void main(String [ ] args) {
        try {
            ServidorImpl oRemoto = new ServidorImpl();
            //Accedemos a una referencia al registro (rmiregistry) local
            java.rmi.registry.Registry registro =
                    java.rmi.registry.LocateRegistry.createRegistry(1099);
            //registramos el objeto, hablaremos más adelante de re-bind
            registro.rebind("ObjetoHello", oRemoto);
            System.err.println("Servidor preparado");
        } catch (Exception e) {
            System.err.println("Excepción del servidor: "+e.toString());
            e.printStackTrace();
        }
    }
}