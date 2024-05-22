package instagram.rmi.server;

/**
 * Guardamos como multimap informacion del servidor como los usuarios y contraseñas
 */
import instagram.media.Media;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/* Multimap concurrente para la práctica de Distribuidos de 2019. */

public class MultiMap<K, T> {
    private final ConcurrentMap<K, ConcurrentLinkedQueue<T>>  map =
            new ConcurrentHashMap<K, ConcurrentLinkedQueue<T>> ();
    public void push(K clave, T valor) {
        java.util.Queue<T> cola = map.get(clave);
        if (null == cola) {
            // putIfAbsent es atómica pero requiere "nueva", y es costoso
            ConcurrentLinkedQueue<T> nueva = new ConcurrentLinkedQueue<T>();
            ConcurrentLinkedQueue<T> previa = map.putIfAbsent(clave, nueva);
            cola = (null == previa) ? nueva : previa;
        }
        cola.add(valor);
    }
    public T pop(K clave) {
        ConcurrentLinkedQueue<T> cola = map.get(clave);
        return (null !=  cola) ? cola.poll() : null ;
    }

    public boolean existe(K clave, T valor) {
        try {
            ConcurrentLinkedQueue<T> cola = map.get(clave);
            return cola.contains(valor);
        }
        catch (NullPointerException e){return false;}
    }

    public boolean existe(K clave) {
        ConcurrentLinkedQueue<T> cola = map.get(clave);
        return null !=  cola;
    }

    public void delete(K clave){
        map.remove(clave);
    }


    public T getValor(K clave) {
        ConcurrentLinkedQueue<T> cola = map.get(clave);
        return (null!=  cola)? cola.peek() : null ;
    }

    public boolean remove(K clave, T valor) {
        ConcurrentLinkedQueue<T> cola = map.get(clave);
        if (null!= cola) {
            return cola.remove(valor);
        }
        return false;
    }

}
