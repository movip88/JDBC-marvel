package marstucom;

import helper.Utilidades;
import controller.MarstucomController;
import exceptions.ErroresLogica;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author polmonleonvives
 */
public class MARSTUCOM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Utilidades u = new Utilidades();
        boolean salir = true;

        try {
            MarstucomController.getInstance().getMarstucomDao().connect();
            while (salir) {
                String comando = u.pedirDatos("");
                try {
                    if (comando.length() == 0) {
                        throw new ErroresLogica(ErroresLogica.WRONG_NUMBER_ARGUMENTS);
                    }
                    args = comando.split(" ", 2);
                    try {
                        switch (args[0].toUpperCase()) {
                            case "R":
                                checkArguments(args[1].split(" "), 3);
                                System.out.println(MarstucomController.getInstance().registarUser(args[1].split(" ")));
                                break;
                            case "V":
                                checkArguments(args, 1);
                                System.out.println(MarstucomController.getInstance().verHeroes());
                                break;
                            case "L":
                                checkArguments(args[1].split(" "), 2);
                                System.out.println(MarstucomController.getInstance().login(args[1].split(" ")));
                                break;
                            case "S":
                            case "N":
                            case "E":
                            case "W":
                                checkArguments(args, 1);
                                System.out.println(MarstucomController.getInstance().desplazarse(args[0]));
                                break;
                            case "G":
                                System.out.println(MarstucomController.getInstance().obtenerGema(args[1]));
                                break;
                            case "B":
                                checkArguments(args, 2);
                                System.out.println(MarstucomController.getInstance().luchar(args[1]));
                                break;
                            case "D":
                                checkArguments(args, 2);
                                System.out.println(MarstucomController.getInstance().borrarUser(args[1]));
                                break;
                            case "K":
                                checkArguments(args, 1);
                                System.out.println(MarstucomController.getInstance().verRanking());
                                break;
                            case "X":
                                checkArguments(args, 1);
                                salir = false;
                                System.out.println("Adios te esperamos pronto, el universo no aguantar mucho sin ti campeon :)");
                            default:
                                throw new ErroresLogica(ErroresLogica.WRONG_COMMAND);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new ErroresLogica(ErroresLogica.WRONG_NUMBER_ARGUMENTS);
                    }
                } catch (ErroresLogica ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                MarstucomController.getInstance().getMarstucomDao().disconnect();
            } catch (SQLException ex) {
                Logger.getLogger(MARSTUCOM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Comprueba que la alargada de un array no sea diferente a un numero que tu
     * le pases, util para comprobar los argumentos de un comando
     *
     * @param data - String[]
     * @param argumentos - int
     * @throws ErroresLogica
     */
    public static void checkArguments(String[] data, int argumentos) throws ErroresLogica {
        if (data.length != argumentos) {
            throw new ErroresLogica(ErroresLogica.WRONG_NUMBER_ARGUMENTS);
        }
    }
}
