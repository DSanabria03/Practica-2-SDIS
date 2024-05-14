package mvc.client;

import service.HolaVista;

/**
 * MVC 2024
 * Implementación de la Vista
 * @author hector
 */
public class HolaVistaImpl
         extends java.rmi.server.UnicastRemoteObject
         implements HolaVista {

    public HolaVistaImpl() throws java.rmi.RemoteException {
        super();
    }

    //recibe un array con los saludos de la sala de reuniones y lo imprime.
    public void notify(String[] saludos) throws java.rmi.RemoteException {

        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();

        System.out.println("--------------lista de usuarios");

        for (String saludo : saludos)
            System.out.println(saludo);

        System.out.println("----------------------fin lista");
    }
}