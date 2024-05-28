package instagram.rmi.client.stream.unit;

import instagram.media.Media;
import instagram.rmi.client.stream.InstagramClientImpl;
import instagram.rmi.common.Instagram;

public class ClientPlayStory {
    public static void main(String[] args) {
        //String host = (args.length < 1) ? "localhost:1099" : args[0];
        try {
            javax.rmi.ssl.SslRMIClientSocketFactory rmicsf = new javax.rmi.ssl.SslRMIClientSocketFactory();
            java.rmi.registry.Registry reg =
                    java.rmi.registry.LocateRegistry.getRegistry("localhost", 1099, rmicsf);
            Instagram or =
                    (Instagram) reg.lookup("ObjetoHello");
            Media media = new Media("Video1");
            InstagramClientImpl client = new InstagramClientImpl();
            client.launchMediaPlayer(media);
            if(client.isMediaPlayerActive()) {
                client.startStream(media, "localhost", 1099);
            }
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
