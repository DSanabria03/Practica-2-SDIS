package instagram.rmi.client.unit;
import instagram.media.Media;
import instagram.rmi.common.Instagram;

import java.rmi.Naming;

public class Cliente {
    public static void main(String [ ] args) {
        //String host = "192.168.233.58:55555";
        String host = (args.length < 1) ? "localhost:1099" : args[0];
        try {
            javax.rmi.ssl.SslRMIClientSocketFactory rmicsf = new javax.rmi.ssl.SslRMIClientSocketFactory();
            java.rmi.registry.Registry reg =
                java.rmi.registry.LocateRegistry.getRegistry("localhost", 1099/*, rmicsf*/);
            Instagram or =
                    (Instagram) reg.lookup("ObjetoHello");
            System.out.println(or);
            Media var = new Media("Video1");
            or.add2L(var);
            String res = or.getDirectoryList();
            System.out.println("[Respuesta: "+res+"]");
        } catch (java.rmi.RemoteException re) {
            System.err.println("<Cliente: Excepción RMI: "+re);
            re.printStackTrace();
        } catch (Exception e) {
            System.err.println("<Cliente: Excepcion: "+e);
            e.printStackTrace();
        }
    }
}