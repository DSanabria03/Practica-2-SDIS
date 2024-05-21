package instagram.rmi.common;
import instagram.media.Media;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InstagramServer extends Remote {
    Boolean setClientStreamReceptor(InstagramClient cliente) throws RemoteException;
    String randomPlay() throws RemoteException;
    String startMedia(Media story) throws RemoteException, FileNotFoundException;
}
