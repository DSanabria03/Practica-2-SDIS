package instagram.rmi.common;
import instagram.media.Media;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Instagram extends Remote {
    String hello() throws RemoteException;
    String auth(String userName, String pass) throws RemoteException;
    void add2L(Media vid) throws RemoteException;
    void add2L(String playList ,Media vid) throws RemoteException;
    Media readL() throws RemoteException;
    Media readL(String playList) throws RemoteException;
    Media peekL() throws RemoteException;
    Media peekL(String playList) throws RemoteException;
    String deleteL(String playList) throws RemoteException;
    String getDirectoryList() throws RemoteException;
    Media retrieveMedia(String id) throws RemoteException;
    String addLike(String id) throws RemoteException;
    String addComent(String id, String comment) throws RemoteException;
    String setCover(Media vid) throws RemoteException;
}
