package instagram.rmi.client.stream.unit;

import instagram.media.Media;
import instagram.rmi.client.stream.InstagramClientImpl;
import instagram.rmi.common.Instagram;
import instagram.rmi.common.InstagramServer;

public class PlayStory {
    public static void main(String[] args) {
        try {
            javax.rmi.ssl.SslRMIClientSocketFactory rmicsf = new javax.rmi.ssl.SslRMIClientSocketFactory();
            javax.rmi.ssl.SslRMIServerSocketFactory rmissf = new javax.rmi.ssl.SslRMIServerSocketFactory();
            java.rmi.registry.Registry reg =
                    java.rmi.registry.LocateRegistry.getRegistry("localhost", 1099, rmicsf);
            Instagram or =
                    (Instagram) reg.lookup("ObjetoHello");
            Media media = new Media("Video1");
            or.add2L(media);
            InstagramServer server = (InstagramServer) reg.lookup("ObjetoHello");
            InstagramClientImpl client = new InstagramClientImpl(rmicsf,rmissf);
            server.setClientStreamReceptor(client);
            server.startMedia(media);
            System.out.println(or);
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
