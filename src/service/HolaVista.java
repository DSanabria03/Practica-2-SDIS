package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Observador-Vista en MVC: callback RMI
public interface HolaVista extends Remote {

    public void notify(String[] saludos)
            throws RemoteException;
}