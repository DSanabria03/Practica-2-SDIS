package instagram.rmi.server;

import instagram.rmi.common.Instagram;
import instagram.media.Media;
import instagram.media.Globals;
import instagram.rmi.common.InstagramClient;
import instagram.rmi.common.InstagramServer;
import instagram.stream.ServerStream;

import java.util.concurrent.ConcurrentHashMap;

import java.rmi.RemoteException;

public class InstagramServerImpl
        extends java.rmi.server.UnicastRemoteObject
        implements InstagramServer,Instagram {
        private InstagramClient cliente;
    ConcurrentHashMap<String, String> users = new ConcurrentHashMap<>();

    MultiMap<String, Media> reels =
            new MultiMap<String, Media> ();

    ConcurrentHashMap<String, Media> directory = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, InstagramClient> clients = new ConcurrentHashMap<>();

    public InstagramServerImpl(java.rmi.server.RMIClientSocketFactory rmicsf,
                               java.rmi.server.RMIServerSocketFactory rmissf)
            throws java.rmi.RemoteException {

        super(0, rmicsf, rmissf);  //es el constructor de UnicastRemoteObject.
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
        this.add2L("DEFAULT", vid);
    }

    public void add2L(String playList, Media vid) throws RemoteException {
        Media video = directory.get(vid.getName());
        if(null == video){
            directory.put(vid.getName(), vid);
            reels.push(playList, vid);
        } else{
            reels.push(playList, video);
        }
    }

    public Media readL() throws RemoteException {
        return reels.pop("DEFAULT");
    }

    public Media readL(String playList) throws RemoteException {
        return reels.pop(playList);
    }

    public Media peekL() throws RemoteException {
        return reels.getValor("DEFAULT");
    }

    public Media peekL(String playList) throws RemoteException {
        return reels.getValor(playList);
    }

    public String deleteL(String playList) throws RemoteException {
        Boolean cola = reels.existe(playList);
        if (null == cola) {
            return "EMPTY";
        }
        reels.delete(playList);
        return "DELETED";
    }

    public String getDirectoryList() throws RemoteException {
        return directory.keySet().toString();
    }

    public Media retrieveMedia(String id) throws RemoteException {
        return directory.get(id);
    }

    public String addLike(String id) throws RemoteException {
        try{
            Media elemento = retrieveMedia(id);
            elemento.addLike();
            return "SE HA AÑADIDO UN LIKE";
        }catch (Exception e){
            return e.toString();
        }
    }

    public String addComent(String id, String comment) throws RemoteException {
        if(comment.length() > 100){
            return "SE HA SUPERADO EL NÚMERO MÁXIMO DE CARACTERES";
        }
        try{
            Media elemento = retrieveMedia(id);
            elemento.addComment(comment);
            return "SE HA AGREGADO EL COMENTARIO";
        }catch (Exception e){
            return e.toString();
        }
    }

    public String setCover(Media vid) throws RemoteException {
        try{
            vid.setCover(vid.getCover());
            return "SE HA CAMBIADO LA CARÁTULA";
        }catch (Exception e){
            return e.toString();
        }
    }

    public Boolean setClientStreamReceptor(InstagramClient cliente) throws RemoteException{
        try{
            this.cliente = cliente;
            clients.put(cliente.getIp(),cliente);
            return Boolean.TRUE;
        } catch (Exception e){
            return Boolean.FALSE;
        }
    }

    public String randomPlay(){
        return "";
    }

    public String startMedia(Media mv) throws RemoteException{
        // 1. CHECKS
        if(mv == null){
            return "Media inserted is Null";
        }
        if(null == directory.get(mv.getName())){
            return "Este archivo no se encuentra en el directorio, añadalo";
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