package instagram.rmi.client.stream;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import instagram.media.Globals;
import instagram.media.Media;
import instagram.media.MediaPlayer;
import instagram.rmi.common.InstagramClient;
import instagram.stream.ClientStream;

public class InstagramClientImpl
        extends java.rmi.server.UnicastRemoteObject
        implements InstagramClient {

    private Thread playerThread;
    private String ip;

    public InstagramClientImpl(javax.rmi.ssl.SslRMIClientSocketFactory rmicsf,javax.rmi.ssl.SslRMIServerSocketFactory rmissf) throws java.rmi.RemoteException, UnknownHostException {
        super(0,rmicsf,rmissf);
        this.ip = InetAddress.getLocalHost().getHostAddress();
    }

    public Boolean launchMediaPlayer(Media story) throws java.rmi.RemoteException{
        try{
            MediaPlayer mediaplayer = new MediaPlayer(
                    Globals.player_command,
                    Globals.player_abs_filepath+story.getName()+
                            Globals.file_extension,
                    Globals.player_delay_ms
            );
            playerThread = new Thread(mediaplayer, "mediaPlayer");
            playerThread.start();

        }catch (Exception e){ e.printStackTrace(); return false; }
        return true;
    }
    public Boolean isMediaPlayerActive() throws java.rmi.RemoteException{
        return playerThread.isAlive();
    }

    public void startStream(Media story, String ip, Integer port) throws java.rmi.RemoteException{
        ClientStream cs = new ClientStream(story, ip, port, playerThread);
        new Thread(cs, "clientstream").start();
    }

    public String getIp() throws RemoteException {
        return ip;
    }
}
