package mvc.client;
import service.HolaControl;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * MVC 2024
 * Cliente que accede a la "reunión"
 * @author hector
 */
public class Cliente {

    public static void main(String [] args) {

        String rmiHostComplete;
        String username;

        if (args.length < 2) {
            rmiHostComplete = "localhost:1099/SalaReuniones";
            username = "hector";
            System.out.println("---------------------------------");
            System.out.println("Número de parámetros insuficiente. Parámetros: (0) rmiregistry-host-port; (1) id-obj-remoto; (2) username ");
            System.out.println("---------------------------------");
            System.out.println("Usando parámetros hardcoded por defecto: " + rmiHostComplete + " ~ " + username);
            System.out.println("---------------------------------");
        } else {
            rmiHostComplete = args[0] + "/" + args[1];
            username = args[2];
        }

        rmiHostComplete = "rmi://" + rmiHostComplete;

        try {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Bienvenido " + username + " a la sala de reuniones.");
            System.out.println("Introduce la palabra 'exit' en cualquier momento para salir");
            System.out.println("-----------------------------------------------------------------------");
            Thread.sleep(1000);

            System.out.println("Creando Objeto HolaVistaImplementation");
            System.out.println("-----------------------------------------------------------------------");
            Thread.sleep(1000);




            HolaVistaImpl v = new HolaVistaImpl();



            /**
             * TAKE REMOTE OBJECT FROM RMI-REGISTRY
             */
            System.out.println("Recogiendo Objeto Remoto `SalaReuniones`");
            System.out.println("-----------------------------------------------------------------------");
            Thread.sleep(1000);


            HolaControl c = (HolaControl) java.rmi.Naming.lookup(rmiHostComplete);

            /**
             * EXECUTE HolaControl method .setHola(...), associate VIEW to Controller, then Model
             * - "login"
             */
            System.out.println("Conectándose con usuario: " + username);
            System.out.println("-----------------------------------------------------------------------");

            c.setHola(username, v);

            //Thread.sleep(7000);  //siete segundos de espera + desregistro
            BufferedReader tec = new BufferedReader(new InputStreamReader(System.in));

            while(!(tec.readLine()).equals("exit")){
            }

            /**
             * EXECUTE HolaControl method .unsetHola(...), de-activates VIEW to Controller, then Model
             */
            c.unsetHola(v);

            /**
             * MSGs
             */
            System.out.println("Despedida y cierre");
            System.exit(0);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
