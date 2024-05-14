package mvc.server;

import service.HolaControl;
import service.HolaVista;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

/**
 * MVC 2024
 * Implementación del Controlador
 * @author hector
 */
public class HolaControlImpl
         extends java.rmi.server.UnicastRemoteObject
         implements HolaControl {

    HolaModelo modelo;  //el controlador tiene una referencia al modelo

    public HolaControlImpl(HolaModelo modelo)
            throws java.rmi.RemoteException {
        super();
        this.modelo = modelo;
    }

    public void setHola(String saludo, HolaVista vista) throws RemoteException {


        try {
            System.out.println("Saludo de cliente: "+saludo+ " desde: " + this.getClientHost());  // printout de depuración
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        modelo.xxxxsetHola(saludo, vista);
        // si estuvieramos en un Web MVC, setHola()
        // podría encargarse de la multidifusión a los observadores.
    }

    public void unsetHola(HolaVista vista) throws RemoteException {

        try {
            System.out.println("Unset de cliente desde: " + this.getClientHost());  // printout de depuración
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }

        modelo.xxxxunsetHola(vista);
    }
}