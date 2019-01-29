package exceptions;

/**
 * Clase encargada de controlar las excepciones de logica de la aplicacion
 *
 * @author polmonleonvives
 */
public class ErroresLogica extends Exception {

    public static int WRONG_COMMAND = 0;
    public static int WRONG_NUMBER_ARGUMENTS = 1;
    public static int WRONG_SUPERHERO_NAME = 2;
    public static int USER_EXISTS = 3;
    public static int USER_INCORRECT = 4;
    public static int NOT_LOGGED = 5;
    public static int NO_GEM = 6;
    public static int ERROR_DIRECTION = 7;
    public static int FINISHED_GAME = 8;
    public static int WRONG_PASS = 9;
    public static int NO_ENEMY = 10;

    private String[] errores = {"Wrong command", "Wrong number of arguments", "There isn't a superhero whith that name", "User already exists", "username or password is incorrect", "You are not logged in", "Here there is no gem with that name", "You can't move in that direction", "You already finish your game", "Delete aborted. Your password is wrong", "Here there is no enemy with that name"};

    private int code;

    public ErroresLogica(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        if (code >= this.errores.length) {
            return super.getMessage();
        }
        return this.errores[this.code];
    }

}
