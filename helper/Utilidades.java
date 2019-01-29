package helper;

import java.io.*;

/**
 * Esta clase sirve para comunicarse con el usuario
 *
 * @author polmonleonvives
 */
public class Utilidades {

    public Utilidades() {
    }

    /**
     * Este metodo imprime un mesage y devuelve un String con los daros
     * introducidos por el usuario
     *
     * @param msg String
     * @return String
     */
    public String pedirDatos(String msg) {
        BufferedReader brd1 = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(msg);
        try {
            return brd1.readLine();
        } catch (Exception e) {
            return "fallo";
        }
    }

    /**
     * Este metodo pide un numero entero y se asegura de que le usuario
     * interoduzca un numero y lo devuelve
     *
     * @param msg String
     * @param error String
     * @return int
     */
    public int pedirNumeroEntero(String msg, String error) {
        BufferedReader brd1 = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(msg);
        while (true) {
            try {
                return Integer.parseInt(brd1.readLine());
            } catch (NumberFormatException ex) {
                System.out.println(error);
            } catch (Exception e) {
                System.out.println("ERROR");
            }
        }
    }

    /**
     * Este metodo se le pasa un maximo y un minimo y se asegura que el usuario
     * introduzca un numero que este entre estos dos nuemros
     *
     * @param msg String
     * @param error String
     * @param min int
     * @param max int
     * @return int
     */
    public int pedirRangoEnteros(String msg, String error, int min, int max) {
        BufferedReader brd1 = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(msg);
        while (true) {
            try {
                int num = Integer.parseInt(brd1.readLine());
                if (num >= min && num <= max) {
                    return num;
                } else {
                    System.out.println("Tiene que ser un valor entre los valores dichos anteriormente...");
                }
            } catch (NumberFormatException ex) {
                System.out.println(error);
            } catch (Exception e) {
                System.out.println("ERROR");
            }
        }
    }

    /**
     * Este metodo sirve para asegurar una accion y devuelve un boolean con
     * nuestra respuesta
     *
     * @param msg String
     * @return boolean
     */
    public boolean asegurarAccion(String msg) {
        BufferedReader brd1 = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(msg + " (S,n)");
        try {
            String respuesta = brd1.readLine();
            return respuesta.toUpperCase().equals("S");
        } catch (Exception e) {
            return false;
        }
    }
}
