package persistence;

import exceptions.ErroresLogica;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Enemy;
import model.Entity;
import model.Gem;
import model.Place;
import model.RankingDTO;
import model.SuperHero;
import model.User;

/**
 * Clase encargada de hacer todas las operaciónes con la bbdd
 *
 * @author polmonleonvives
 */
public class MarstucomDao {

    Connection connection;

    /**
     * Seleciona todos los herues de la bbdd, y se trae toda su información
     *
     * @return - ArrayList<SuperHero>
     * @throws SQLException
     */
    public ArrayList<SuperHero> selectAllHeroes() throws SQLException {
        Statement st = connection.createStatement();
        String select = "select * from superhero";
        ResultSet rs = st.executeQuery(select);
        ArrayList<SuperHero> heros = new ArrayList();
        while (rs.next()) {
            heros.add(new SuperHero(rs.getString("name"), rs.getString("superpower")));
        }
        rs.close();
        st.close();
        return heros;
    }

    /**
     * Selecciona todos los enemigos que hay en un sito con todos sus datos
     *
     * @param p - Place
     * @return - ArrayList<Enemy>
     * @throws SQLException
     */
    public ArrayList<Enemy> selectAllEnemiesFromPlace(Place p) throws SQLException {
        Statement st = connection.createStatement();
        String select = "select * from enemy where place='" + p.getName() + "'";
        ResultSet rs = st.executeQuery(select);
        ArrayList<Enemy> enemies = new ArrayList();
        while (rs.next()) {
            enemies.add(new Enemy(rs.getString("debility"), rs.getString("name"), rs.getInt("level"), p));
        }
        rs.close();
        st.close();
        return enemies;
    }

    /**
     * Selecciona todos los enemigos que hay en un sito con una carga perezosa
     * solo se trae el nombre
     *
     * @param p - Place
     * @return - ArrayList<Enemy>
     * @throws SQLException
     */
    public ArrayList<Enemy> selectAllEnemiesFromPlaceLazy(Place p) throws SQLException {
        Statement st = connection.createStatement();
        String select = "select * from enemy where place='" + p.getName() + "'";
        ResultSet rs = st.executeQuery(select);
        ArrayList<Enemy> enemies = new ArrayList();
        while (rs.next()) {
            enemies.add(new Enemy(rs.getString("name")));
        }
        rs.close();
        st.close();
        return enemies;
    }

    /**
     * Seleccion un enemigo a partir de su nombre
     *
     * @param name - String
     * @return - Enemy
     * @throws SQLException
     */
    public Enemy selectEnemieByName(String name) throws SQLException {
        Statement st = connection.createStatement();
        String select = "select * from enemy where name='" + name + "'";
        ResultSet rs = st.executeQuery(select);
        Enemy e = null;
        if (rs.next()) {
            e = new Enemy(rs.getString("debility"), rs.getString("name"), rs.getInt("level"), new Place(rs.getString("place")));
        }
        rs.close();
        st.close();
        return e;
    }

    /**
     * Selecciona la lista de gemas de un usuario con todos sus datos
     *
     * @param u - user
     * @return - ArrayList<Gem>
     * @throws SQLException
     */
    private ArrayList<Gem> selectAllGemsUser(User u) throws SQLException {
        Statement st = connection.createStatement();
        String select = "select * from gem where user='" + u.getUsername() + "'";
        ResultSet rs = st.executeQuery(select);
        ArrayList<Gem> gems = new ArrayList();
        while (rs.next()) {
            Gem g = new Gem(rs.getString("name"));
            g.setPlace(new Place(rs.getString("place")));
            g.setUser(new User(u.getUsername()));
            g.setOwner(rs.getString("owner") == null ? null : new Entity(rs.getString("owner")));
            gems.add(g);
        }
        rs.close();
        st.close();
        return gems;
    }

    /**
     * Selecciona todos los sitios de la bbdd pero se los trae en ligero ya que
     * solo contienen el nombre
     *
     * @return - ArrayList<Place>
     * @throws SQLException
     */
    public ArrayList<Place> selectAllPlacesLazy() throws SQLException {
        Statement st = connection.createStatement();
        String select = "select distinct name from place";
        ResultSet rs = st.executeQuery(select);
        ArrayList<Place> sitios = new ArrayList();
        while (rs.next()) {
            sitios.add(new Place(rs.getString("name")));
        }
        rs.close();
        st.close();
        return sitios;
    }

    /**
     * Selecciona un sitio con todos sus datos pero los atributos de direcciones
     * son Places en ligero ya que solo tienen el nombre
     *
     * @param p - PLace
     * @return - Place
     * @throws SQLException
     */
    private Place selectPlaceByName(Place p) throws SQLException {
        Statement st = connection.createStatement();
        String select = "select * from place where name='" + p.getName() + "'";
        ResultSet rs = st.executeQuery(select);
        if (rs.next()) {
            p.setDescription(rs.getString("description"));
            p.setNorth(rs.getString("north") == null ? null : new Place(rs.getString("north")));
            p.setEast(rs.getString("east") == null ? null : new Place(rs.getString("east")));
            p.setSouth(rs.getString("south") == null ? null : new Place(rs.getString("south")));
            p.setWest(rs.getString("west") == null ? null : new Place(rs.getString("west")));
        }
        rs.close();
        st.close();
        return p;
    }

    /**
     * Selecciona un SueprHero a partir de su nombre, si no lo encuentra retorna
     * null
     *
     * @param s - String
     * @return - SuperHero
     * @throws SQLException
     */
    private SuperHero selectHeroByName(String s) throws SQLException {
        Statement st = connection.createStatement();
        String select = "select * from superhero where name='" + s + "'";
        ResultSet rs = st.executeQuery(select);
        SuperHero hero = null;
        if (rs.next()) {
            hero = new SuperHero(rs.getString("name"), rs.getString("superpower"));
        }
        rs.close();
        st.close();
        return hero;
    }

    /**
     * Selecciona todos los enemigos que hay en un sitio y genera una lista de
     * nemigos ligera ya que solo carga el nombre
     *
     * @param p - Place
     * @return - ArrayList<Enemy>
     * @throws SQLException
     */
    public ArrayList<Enemy> selectAllEnemysPlaceLazy(Place p) throws SQLException {
        Statement st = connection.createStatement();
        String select = "select * from enemy where place = '" + p.getName() + "'";
        ResultSet rs = st.executeQuery(select);
        ArrayList<Enemy> enemigos = new ArrayList();
        while (rs.next()) {
            enemigos.add(new Enemy(rs.getString("name")));
        }
        rs.close();
        st.close();
        return enemigos;
    }

    /**
     * Devuelve una lista ordenada de RankingDTO con todos los players de la
     * bbdd
     *
     * @return - ArrayList<RankingDTO>
     * @throws SQLException
     */
    public ArrayList<RankingDTO> cathRanking() throws SQLException {
        Statement st = connection.createStatement();
        String select = "select username, superhero, (select count(*) from gem where user=username and owner=username) as gems,level ,points from user order by gems desc, level desc, points desc";
        ResultSet rs = st.executeQuery(select);
        ArrayList<RankingDTO> ranking = new ArrayList();
        while (rs.next()) {
            User user = new User(rs.getString("username"));
            user.setLevel(rs.getInt("level"));
            user.setPoints(rs.getInt("points"));
            ranking.add(new RankingDTO(user, new SuperHero(rs.getString("superhero")), rs.getInt("gems")));
        }
        rs.close();
        st.close();
        return ranking;
    }

    /**
     * Reccive un usuario si el usuario existe le carga todos sus datos, en caso
     * de que no lo encuentre lanza una execption de Errores Logica
     *
     * @param u - User
     * @return - User
     * @throws SQLException
     * @throws ErroresLogica
     */
    public User validateUser(User u) throws SQLException, ErroresLogica {
        Statement st = connection.createStatement();
        String select = "select * from user where username='" + u.getUsername() + "' and pass='" + u.getPass() + "'";
        ResultSet rs = st.executeQuery(select);
        User user = new User();
        if (rs.next()) {
            user.setUsername(rs.getString("username"));
            user.setPass(rs.getString("pass"));
            user.setLevel(rs.getInt("level"));
            user.setPoints(rs.getInt("points"));
            user.setPlace(selectPlaceByName(new Place(rs.getString("place"))));
            user.setSuperHero(selectHeroByName(rs.getString("superhero")));
            user.setListGems(selectAllGemsUser(user));
            rs.close();
            st.close();
        } else {
            rs.close();
            st.close();
            throw new ErroresLogica(ErroresLogica.USER_INCORRECT);
        }
        return user;
    }

    /**
     * Este metodo actualiza la posicion de un usuario en la bbdd a partir de la
     * que tiene la instanci de user, se utila una transaccion para asegurar de
     * que cuando un usuario se mueva todas us gemas tb se muevan
     *
     * @param u - User
     * @return - User
     * @throws SQLException
     */
    public User moveUser(User u) throws SQLException {
        connection.setAutoCommit(false);
        try {
            PreparedStatement ps = connection.prepareStatement("update user set place = ? where username = ?");
            ps.setString(1, u.getPlace().getName());
            ps.setString(2, u.getUsername());
            ps.executeUpdate();
            ps.close();
            u.setPlace(selectPlaceByName(u.getPlace()));
            updatePositionGemsOwner(u);
            u.setListGems(selectAllGemsUser(u));
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(true);
        }

        return u;
    }

    /**
     * Este metodo actualiza la posicion de las gemas a partir de la posicion de
     * una entidad
     *
     * @param e - entity
     * @throws SQLException
     */
    private void updatePositionGemsOwner(Entity e) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update gem set place = ? where owner = ?");
        ps.setString(1, e.getPlace().getName());
        ps.setString(2, e.getUsername());
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Esta funcion recive un usuario y actualza sus puntos y nivel a la bbdd
     *
     * @param u - User
     * @throws SQLException
     */
    public void updateLevelAndPointsUser(User u) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update user set level = ?, points = ? where username = ?");
        ps.setInt(1, u.getLevel());
        ps.setInt(2, u.getPoints());
        ps.setString(3, u.getUsername());
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Esta funcion recime un enemigo y un user y traspasa todas las gemas del
     * usuario al enemigo y actualiza la lista de gemas del usuario
     *
     * @param u - User
     * @param e - Enemy
     * @throws SQLException
     */
    public void pasGemsUserToEnemy(User u, Enemy e) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update gem set owner = ? where owner = ?");
        ps.setString(1, e.getUsername());
        ps.setString(2, u.getUsername());
        ps.executeUpdate();
        ps.close();
        u.setListGems(selectAllGemsUser(u));
    }

    /**
     * Esta funcion deja el owner de todas sus gemas de una misma partida en
     * null
     *
     * @param u - User
     * @param e - Enemy
     * @throws SQLException
     */
    public void enemyReleaseGems(User u, Enemy e) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update gem set owner = null where owner = ? and user = ?");
        ps.setString(1, e.getUsername());
        ps.setString(2, u.getUsername());
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Selecciona todas las gemas de una entidad en una misma partida
     *
     * @param e - Entity
     * @param u - User
     * @return ArrayList<Gem>
     * @throws SQLException
     */
    public ArrayList<Gem> getAllGemsOfEntityInPartyLazy(Entity e, User u) throws SQLException {
        Statement st = connection.createStatement();
        String select = "select name from gem where owner='" + e.getUsername() + "' and user ='" + u.getUsername() + "'";
        ResultSet rs = st.executeQuery(select);
        ArrayList<Gem> gems = new ArrayList();
        while (rs.next()) {
            gems.add(new Gem(rs.getString("name")));
        }
        rs.close();
        st.close();
        return gems;
    }

    /**
     * Esta funcion es trasaccional apara asegurar que un enemigo se mueva
     * conjuntamente con todas sus gemas, selecciona un sitio aleatorio en la
     * bbdd y se lo seta al enemigo
     *
     * @param e - Enemy
     * @throws SQLException
     */
    public void updateRandomPlaceEnemy(Enemy e) throws SQLException {
        connection.setAutoCommit(false);
        try {
            PreparedStatement ps = connection.prepareStatement("update enemy set place = (select name from place where name != ? order by rand() limit 1) where name = ?");
            ps.setString(1, e.getPlace().getName());
            ps.setString(2, e.getUsername());
            ps.executeUpdate();
            ps.close();
            updatePositionGemsOwner(e);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    /**
     * Primero comprueba que la gema se pueda coger en caso negativo genera una
     * exception de ErroresLogica,
     *
     * @param u - user
     * @param g - Gem
     * @return - int
     * @throws SQLException
     * @throws ErroresLogica
     */
    public int cathGem(User u, Gem g) throws SQLException, ErroresLogica {
        if (!posibleCathGem(u, g)) {
            throw new ErroresLogica(ErroresLogica.NO_GEM);
        }
        PreparedStatement ps = connection.prepareStatement("update gem set owner = ? where user = ? and name = '" + g.getName() + "'");
        ps.setString(1, u.getUsername());
        ps.setString(2, u.getUsername());
        ps.executeUpdate();
        ps.close();
        int tmp = numGemsUser(u);
        return tmp;
    }

    /**
     * Selecciona todas las gemas de las cual es propietario un usuario y
     * devuelve el numero
     *
     * @param u - User
     * @return - int
     * @throws SQLException
     */
    public int numGemsUser(User u) throws SQLException {
        Statement st = connection.createStatement();
        String select = "select * from gem where owner='" + u.getUsername() + "'";
        ResultSet rs = st.executeQuery(select);
        rs.last();
        int tmp = rs.getRow();
        rs.close();
        st.close();
        return tmp;
    }

    /**
     * Esta operación es trasaccional para asegurar que cuando se inserte un
     * usuario se inserte tb las 6 gemas relacionadas a su partida
     *
     * @param u - User
     * @throws SQLException
     * @throws ErroresLogica
     */
    public void insertUser(User u) throws SQLException, ErroresLogica {
        if (existUser(u)) {
            throw new ErroresLogica(ErroresLogica.USER_EXISTS);
        }
        connection.setAutoCommit(false);
        try {
            PreparedStatement ps = connection.prepareStatement("insert into user values (?, ?, ?, ?, ?, ?)");
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPass());
            ps.setInt(3, u.getLevel());
            ps.setString(4, u.getSuperHero().getName());
            ps.setString(5, u.getPlace().getName());
            ps.setInt(6, u.getPoints());
            ps.executeUpdate();
            ps.close();
            for (Gem g : u.getListGems()) {
                insertGem(g);
            }
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(true);
        }

    }

    /**
     * Esta operacion eleimina un usuario de la bbdd
     *
     * @param u - User
     * @throws SQLException
     */
    public void deleteUser(User u) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("delete from user where username=?");
        ps.setString(1, u.getUsername());
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Se encarga de insertar una gema en la bbdd
     *
     * @param g - Gem
     * @throws SQLException
     */
    private void insertGem(Gem g) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into gem values (?, ?, ?, ?)");
        ps.setString(1, g.getName());
        ps.setString(2, g.getUser().getUsername());
        ps.setString(3, g.getOwner() == null ? null : g.getOwner().getUsername());
        ps.setString(4, g.getPlace().getName());
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Se encarga de comprobar si existe un usuario con el mismo nombre en la
     * bbdd
     *
     * @param u - User
     * @return boolean
     * @throws SQLException
     */
    private boolean existUser(User u) throws SQLException {
        Statement st = connection.createStatement();
        String query = "select * from user where username ='" + u.getUsername() + "'";
        ResultSet rs = st.executeQuery(query);
        boolean tmp = rs.next();
        rs.close();
        return tmp;
    }

    /**
     * Se encarga de comprobar si existe un sper heroe con el mismo nombre en la
     * bbdd
     *
     * @param s - SeprHero
     * @return boolean
     * @throws SQLException
     */
    public boolean existSuperHero(SuperHero s) throws SQLException {
        Statement st = connection.createStatement();
        String query = "select * from superhero where name ='" + s.getName() + "'";
        ResultSet rs = st.executeQuery(query);
        boolean tmp = rs.next();
        rs.close();
        return tmp;
    }

    /**
     * Comprueba que una gema este en el lugar indicado no tenga propietario y
     * que el nombre sea el mismo
     *
     * @param u - User
     * @param g - Gem
     * @return - boolean
     * @throws SQLException
     */
    private boolean posibleCathGem(User u, Gem g) throws SQLException {
        Statement st = connection.createStatement();
        String query = "select * from gem where place = '" + g.getPlace().getName() + "' and user='" + u.getUsername() + "' and owner is null and name = '" + g.getName() + "'";
        ResultSet rs = st.executeQuery(query);
        boolean tmp = rs.next();
        rs.close();
        return tmp;
    }

    /**
     * realiza la conexion a la bbdd
     *
     * @throws SQLException
     */
    public void connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/marvel";
        String user = "root";
        String pass = "";
        connection = DriverManager.getConnection(url, user, pass);
    }

    /**
     * si la conexion es diferente a null desconecta de la bbdd
     *
     * @throws SQLException
     */
    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
