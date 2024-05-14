package service;

import java.rmi.RemoteException;

public interface HolaControl extends java.rmi.Remote {

    public void setHola(String saludo, HolaVista vista) throws RemoteException;
    public void unsetHola(HolaVista vista) throws RemoteException;
}