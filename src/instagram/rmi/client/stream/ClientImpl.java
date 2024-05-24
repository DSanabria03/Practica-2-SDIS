package instagram.rmi.client.stream;

import instagram.media.Globals;
import instagram.media.Media;
import instagram.media.MediaPlayer;
import instagram.stream.ClientStream;

public class ClientImpl {

    private Thread playerThread;

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
    };
    public Boolean isMediaPlayerActive() throws java.rmi.RemoteException{
        return playerThread.isAlive();
    };

    public void startStream(Media story, String ip, Integer port) throws java.rmi.RemoteException{
        ClientStream cs = new ClientStream(story, ip, port, playerThread);
        new Thread(cs, "clientstream").start();
    };

}
