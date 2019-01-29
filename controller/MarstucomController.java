package controller;

import exceptions.ErroresLogica;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import model.Enemy;
import model.Gem;
import model.Place;
import model.RankingDTO;
import model.SuperHero;
import model.User;
import persistence.MarstucomDao;

/**
 * Clase encargada de controlar la logica de toda la aplicación
 *
 * @author user88
 */
public class MarstucomController {

    public static MarstucomController instance = null;

    private MarstucomDao marstucomDao;
    private User user;
    private List<String> nombreGemas = Arrays.asList("Mind Gem", "Power Gem", "Reaity Gem", "Soul Gem", "Space Gem", "Time Gem");

    /**
     * Metodo que comprueva que no se haya creado la instancia estatica en caso
     * de que no este creada la crea y la devuelve y si ya esta la devuelve
     *
     * @return MarstucomController
     */
    public static MarstucomController getInstance() {
        if (instance == null) {
            instance = new MarstucomController();
        }
        return instance;
    }

    private MarstucomController() {
        marstucomDao = new MarstucomDao();
        user = null;
    }

    public MarstucomDao getMarstucomDao() {
        return marstucomDao;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Recive un array con toda la información de un usuario si no exixste
     * ninguna con el mismo nombre lo crea y tab crea las 6 gema relacionadas
     * con supartida
     *
     * @param data - String[]
     * @return String
     * @throws ErroresLogica
     * @throws SQLException
     */
    public String registarUser(String[] data) throws ErroresLogica, SQLException {
        SuperHero s = new SuperHero(data[2]);
        if (!marstucomDao.existSuperHero(s)) {
            throw new ErroresLogica(ErroresLogica.WRONG_SUPERHERO_NAME);
        }
        User user = new User(data[1], s, 0, data[0], 1, new Place("New York"));
        ArrayList<Place> sitios = marstucomDao.selectAllPlacesLazy();
        for (String nombreGema : nombreGemas) {
            Gem g = new Gem(nombreGema);
            boolean added = true;
            Random rand = new Random();
            while (added) {
                int n = rand.nextInt(sitios.size());
                Place p = sitios.get(n);
                if (!p.equals(user.getPlace()) && !existGemUser(user, p)) {
                    g.setUser(user);
                    g.setPlace(p);
                    ArrayList<Enemy> enemigos = marstucomDao.selectAllEnemiesFromPlaceLazy(p);
                    g.setOwner(enemigos.size() != 0 ? enemigos.get(rand.nextInt(enemigos.size())) : null);
                    user.getListGems().add(g);
                    added = false;
                }
            }
        }
        marstucomDao.insertUser(user);
        return "User registered";
    }

    /**
     * Devuelve un string con todos los super herues
     *
     * @return @throws SQLException
     */
    public String verHeroes() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("- SuperHeros -");
        for (SuperHero s : marstucomDao.selectAllHeroes()) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    /**
     * Recibe un array de dos posiciones con el nombre y la contraseña y
     * comprueva que los datos son correctos si es correcto te logeas y devuelve
     * un String con la respuesta
     *
     * @param data - String[]
     * @return String
     * @throws ErroresLogica
     * @throws SQLException
     */
    public String login(String[] data) throws ErroresLogica, SQLException {
        StringBuilder sb = new StringBuilder();
        user = marstucomDao.validateUser(new User(data[1], data[0]));
        sb.append("Welcome, ").append(user.getUsername()).append("\n");
        infoPlace(sb);
        return sb.toString();
    }

    /**
     * Recibe el nombre de una gema y comprueva que se pueda coger y devuelve un
     * String con la respuesta
     *
     * @param name - String
     * @return - String
     * @throws ErroresLogica
     * @throws SQLException
     */
    public String obtenerGema(String name) throws ErroresLogica, SQLException {
        checkLogged();
        checkFinish();
        StringBuilder sb = new StringBuilder();
        int gems = marstucomDao.cathGem(user, new Gem(name, user.getPlace()));
        sb.append("You have got the gem");
        if (gems == 6) {
            sb.append("\nYOU WIN!!! YOU HAVE ALL GEMS!");
        }
        return sb.toString();
    }

    /**
     * Recibe un nombre del enemigo y realiza una batalla en caso de que sea
     * posible
     *
     * @param name - String
     * @return String
     * @throws ErroresLogica
     * @throws SQLException
     */
    public String luchar(String name) throws ErroresLogica, SQLException {
        checkLogged();
        checkFinish();
        Enemy enemigo = marstucomDao.selectEnemieByName(name);
        if (enemigo == null || !enemigo.getPlace().equals(this.user.getPlace())) {
            throw new ErroresLogica(ErroresLogica.NO_ENEMY);
        }
        return new BattleController(user, enemigo, marstucomDao).runBattle().resolveBattle().resultBattle().toString();
    }

    /**
     * Recibe un String de la direccion a la que quieres desplazarte y devuelve
     * un String con los datos
     *
     * @param direction - String
     * @return String
     * @throws ErroresLogica
     * @throws SQLException
     */
    public String desplazarse(String direction) throws ErroresLogica, SQLException {
        checkLogged();
        checkFinish();
        Place p = null;
        switch (direction.toUpperCase()) {
            case "S":
                p = user.getPlace().getSouth();
                break;
            case "N":
                p = user.getPlace().getNorth();
                break;
            case "E":
                p = user.getPlace().getEast();
                break;
            case "W":
                p = user.getPlace().getWest();
                break;
        }
        user.setPlace(p);
        user = marstucomDao.moveUser(user);
        StringBuilder sb = new StringBuilder();
        sb.append("Moving to ").append(direction.toLowerCase()).append(" ...\n");
        infoPlace(sb);
        return sb.toString();
    }

    /**
     * Recive una contraseña comprueba que sea correcta y si es así borra el
     * usuario
     *
     * @param pass - String
     * @return - String
     * @throws ErroresLogica
     * @throws SQLException
     */
    public String borrarUser(String pass) throws ErroresLogica, SQLException {
        checkLogged();
        if (pass.equals(user.getPass())) {
            marstucomDao.deleteUser(user);
            user = null;
            return "User deleted";
        }
        throw new ErroresLogica(ErroresLogica.WRONG_PASS);
    }

    /**
     * Duebuelve un String con los datos del ranking
     *
     * @return - String
     * @throws SQLException
     */
    public String verRanking() throws SQLException {
        StringBuilder sb = new StringBuilder("- Ranking -");
        for (RankingDTO r : marstucomDao.cathRanking()) {
            sb.append("\n").append(r);
        }
        return sb.toString().equals("- Ranking -") ? "There are no users for the ranking (nobody has gems)" : sb.toString();
    }

    /**
     * Este metodo concatena toda la información de un sitio en un StringBuilder
     * que recive por parametros
     *
     * @param sb - StringBuilder
     * @throws SQLException
     */
    private void infoPlace(StringBuilder sb) throws SQLException {
        sb.append(user.getPlace());
        sb.append("\n---\n");
        sb.append(listEnemies(marstucomDao.selectAllEnemiesFromPlace(user.getPlace())));
        sb.append("\n---\n");
        sb.append(user.listGemsPlace());
        sb.append("\n---\n");
        sb.append(user.getPlace().devolverDirecciones());
    }

    /**
     * Devuelve un String con la información de todos los enemigos de una lista
     *
     * @param listEnemies - ArrayList<Enemy>
     * @return String
     */
    private String listEnemies(ArrayList<Enemy> listEnemies) {
        StringBuilder sb = new StringBuilder("- Enemies -");
        for (Enemy e : listEnemies) {
            sb.append("\n").append(e);
        }
        return sb.toString().equals("- Enemies -") ? "There is nobody here" : sb.toString();
    }

    /**
     * Comprueba que un usuario este loggeado en la aplicación, en caso de que
     * no este lanza una excepcion
     *
     * @throws ErroresLogica
     * @throws SQLException
     */
    private void checkLogged() throws ErroresLogica, SQLException {
        if (user == null) {
            throw new ErroresLogica(ErroresLogica.NOT_LOGGED);
        }
    }

    /**
     * Comprueba que un usuario no tenga las 6 gemas, en caso de que las tenga
     * lanza una excepcion
     *
     * @throws ErroresLogica
     * @throws SQLException
     */
    private void checkFinish() throws ErroresLogica, SQLException {
        if (marstucomDao.numGemsUser(user) == 6) {
            throw new ErroresLogica(ErroresLogica.FINISHED_GAME);
        }
    }

    /**
     * Comprueba que en una haya dos gemas del mismo usuario en el mismo sitio
     *
     * @param u - User
     * @param p - Place
     * @return
     */
    private boolean existGemUser(User u, Place p) {
        for (Gem g : u.getListGems()) {
            if (g.getPlace() != null) {
                if (p.equals(g.getPlace())) {
                    return true;
                }
            }
        }
        return false;
    }
}
