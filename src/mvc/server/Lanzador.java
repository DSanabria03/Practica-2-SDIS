package mvc.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * MVC 2024
 * Server Launcher
 * @author hector
 */
public class Lanzador {

    public static void main(String[] args) {
        try {

            //System.setProperty("java.rmi.server.hostname", "192.168.1.21");

            /**
             * 0.- MODEL CREATION
             */
            HolaModelo saludos = new HolaModelo();

            /**
             * 1.- CREATE REMOTE OBJECT TO PUBLISH
             */
            HolaControlImpl controlador = new HolaControlImpl(saludos);

            /**
             * 2.- LOCATE / CREATE RMI-REGISTRY
             */
            Registry registro = LocateRegistry.createRegistry(1099);

            /**
             * 3.- PUBLISH THE REMOTE OBJECT IN RMI-REGISTRY
             */
            registro.rebind("SalaReuniones", controlador);


            System.out.println("Objeto remoto 'SalaReuniones' enlazado");
        } catch (java.rmi.RemoteException re) {
            re.printStackTrace(System.err);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}