package instagram.rmi.common;

import instagram.media.Media;

import java.rmi.RemoteException;

public interface InstagramClient {
    Boolean  launchMediaPlayer(Media story) throws RemoteException;
    Boolean isMediaPlayerActive() throws RemoteException;
    void startStream(Media story, String ip, Integer port) throws RemoteException;
}
