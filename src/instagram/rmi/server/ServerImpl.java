package instagram.rmi.server;

import instagram.rmi.common.Instagram;
import instagram.media.Media;
import instagram.media.Globals;
import instagram.media.MediaPlayer;
import instagram.rmi.common.InstagramClient;
import instagram.stream.ServerStream;

import java.awt.event.WindowStateListener;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import java.rmi.RemoteException;
import java.rmi.registry.*;



public class ServerImpl
        extends java.rmi.server.UnicastRemoteObject
        implements Instagram {
private InstagramClient cliente;
    ConcurrentHashMap<String, String> users = new ConcurrentHashMap<String, String>();

    ConcurrentMap<String, ConcurrentLinkedQueue<Media>> reels =
            new ConcurrentHashMap<String, ConcurrentLinkedQueue<Media>> ();

    public ServerImpl() throws java.rmi.RemoteException {
        super();  //es el constructor de UnicastRemoteObject.
        users.put("hector", "1234");
        users.put("sdis", "asdf");
    }

    public String hello() throws java.rmi.RemoteException {
        return "Hola Bienvenido a Instagram :D";
    }

    public String auth(String userName, String pass) throws RemoteException {
        try{
            String value = users.get(userName);
            if(value.contains(pass)){
                return "AUTH";
            } else {
                return "NOAUTH";
            }
        }catch (NullPointerException e){
            return "NOAUTH";
        }
    }

    public void add2L(Media vid) throws RemoteException {
        java.util.Queue<Media> cola = reels.get("DEFAULT");
        if (null == cola) {
            // putIfAbsent es atómica pero requiere "nueva", y es costoso
            ConcurrentLinkedQueue<Media> nueva = new ConcurrentLinkedQueue<Media>();
            ConcurrentLinkedQueue<Media> previa = reels.putIfAbsent("DEFAULT", nueva);
            cola = (null == previa) ? nueva : previa;
        }
        cola.add(vid);
    }

    public void add2L(String playList, Media vid) throws RemoteException {
        java.util.Queue<Media> cola = reels.get(playList);
        if (null == cola) {
            // putIfAbsent es atómica pero requiere "nueva", y es costoso
            ConcurrentLinkedQueue<Media> nueva = new ConcurrentLinkedQueue<Media>();
            ConcurrentLinkedQueue<Media> previa = reels.putIfAbsent(playList, nueva);
            cola = (null == previa) ? nueva : previa;
        }
        cola.add(vid);
    }

    public Media readL() throws RemoteException {
        java.util.Queue<Media> cola = reels.get("DEFAULT");
        return cola.poll();
    }

    public Media readL(String playList) throws RemoteException {
        java.util.Queue<Media> cola = reels.get(playList);
        return cola.poll();
    }

    public Media peekL() throws RemoteException {
        java.util.Queue<Media> cola = reels.get("DEFAULT");
        return cola.peek();
    }

    public Media peekL(String playList) throws RemoteException {
        java.util.Queue<Media> cola = reels.get(playList);
        return cola.peek();
    }

    public String deleteL(String playList) throws RemoteException {
        java.util.Queue<Media> cola = reels.get(playList);
        if (null == cola) {
            return "EMPTY";
        }
        reels.remove(playList);
        return "DELETED";
    }

    public String[] getDirectoryList() throws RemoteException {
        Registry registro = LocateRegistry.getRegistry("localhost");
        return registro.list();
    }

    public Media retrieveMedia(String id) throws RemoteException {
        String[] directorio = getDirectoryList();
        Media elemento;
        for (int i = 0; i < directorio.length; i++) {
            if(directorio[i].equals(id)) {
                elemento = new Media(directorio[i]);
                return elemento;
            }
        }
        return null;
    }

    public String addLike(String id) throws RemoteException {
        Media elemento = retrieveMedia(id);
        elemento.addLike();
        return "SE HA AÑADIDO UN LIKE";
    }

    public String addComent(String id, String comment) throws RemoteException {
        if(comment.length() > 100){
            return "SE HA SUPERADO EL NÚMERO MÁXIMO DE CARACTERES";
        }
        Media elemento = retrieveMedia(id);
        elemento.addComment(comment);
        return "SE HA AGREGADO EL COMENTARIO";
    }

    public String setCover(Media vid) throws RemoteException {
        vid.setCover(vid.getCover());
        return "SE HA CAMBIADO LA CARÁTULA";
    }

    public Boolean setClientStreamReceptor(InstagramClient cliente) throws RemoteException{
        try{
            this.cliente = cliente;
            return Boolean.TRUE;
        } catch (Exception e){
            return Boolean.FALSE;
        }
    }


    public String startMedia(Media mv) throws RemoteException{
        // 1. CHECKS
        if(mv == null){
            return "Media class is Null";
        }



        // 2. PREPARE A SERVERSOCKET FOR THE STREAMING
        String pathFile = Globals.path_origin+mv.getName()+Globals.file_extension;
        ServerStream ss = new ServerStream(pathFile, this.cliente);
        new Thread(ss, "streamserver").start();
        try{ Thread.sleep(2000); }
        catch (InterruptedException e) { e.printStackTrace(); }

        // 3. LAUNCH CLIENT MEDIAPLAYER
        System.out.println("- Checking MediaPlayer status...");
        try {
            if (!this.cliente.launchMediaPlayer(mv)) return "Launcher cannot be triggered";
        } catch (Exception e){
            e.printStackTrace();
            return "Error launching Media Player at client";
        }

        // 4. READY FOR STREAMING, PLEASE CLIENT GO GO GO
        System.out.println("- Sending server streaming ready signal..."+Globals.server_host+":"+ss.getServerSocketPort());
        try {
            this.cliente.startStream(mv, Globals.server_host, ss.getServerSocketPort());
        } catch (Exception e){
            e.printStackTrace();
            return "Error during streaming at client";
        }
        return "MEDIA "+mv.getName()+" started";
    }



}