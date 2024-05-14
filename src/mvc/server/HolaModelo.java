package mvc.server;

import service.HolaVista;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * MVC 2024
 * Modelo observable.
 * @author hector
 */
public class HolaModelo {

    Map<HolaVista,String> mapaObservadores = new HashMap();

    // agrega un Observador (HolaVista)
    public void xxxxsetHola(String saludo, HolaVista obs) {
        mapaObservadores.put(obs, saludo);
        System.out.println("Realizado un puts con el saludo: "+saludo);
        notificaObservadores() ;
    }

    public void xxxxunsetHola(HolaVista obs) {
        mapaObservadores.remove(obs);
        notificaObservadores();
    }

    // En muchas ocasiones la notificación se delega en el
    // controlador, sobre todo en interfaces Web.
    void notificaObservadores() {

        String[] lista = new String[1];
        String[] saludos = mapaObservadores.values().toArray(lista);

        System.out.print("[");                           // printout para depurar
        for (int i=0; i<saludos.length; i++) {           // la lista de saludos
            if(saludos[i] != null)
                System.out.print(saludos[i] + ", ");     // que enviamos a los asistentes.
            else
                saludos[i] = "<sitio vacío>";
        }
        System.out.println("]");

        mapaObservadores.forEach( (observador,mensaje) -> {
            try {

                observador.notify(saludos);

            } catch (RemoteException re) {}
        });

    }
    // Aquí podrás poner más métodos para manipular/acceder al estado
    // del modelo desde dentro del servidor o mediante otros controladores.
}