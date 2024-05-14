package instagram.cliente;
import instagram.servidor.Servidor;

import java.rmi.Naming;

public class Cliente {
    public static void main(String [ ] args) {
        //String host = "192.168.233.58:55555";
        String host = (args.length < 1) ? "localhost:1099" : args[0];
        try {
            instagram.servidor.Servidor or =
                    (instagram.servidor.Servidor) Naming.lookup("rmi://localhost:1099/ObjetoHello");
            System.out.println(or);
            String respuesta = or.sayHello();
            System.out.println("[Respuesta: "+respuesta+"]");
        } catch (java.rmi.RemoteException re) {
            System.err.println("<Cliente: Excepción RMI: "+re);
            re.printStackTrace();
        } catch (Exception e) {
            System.err.println("<Cliente: Excepcion: "+e);
            e.printStackTrace();
        }
    }
}