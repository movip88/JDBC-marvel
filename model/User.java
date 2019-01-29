package model;

import java.util.ArrayList;

/**
 * Clase encargada de almacenar la informacion de un usuario, su super clase es
 * entity
 *
 * @author polmonleonvives
 */
public class User extends Entity {

    private String pass;
    private SuperHero superHero;
    private int points;
    private ArrayList<Gem> listGems = new ArrayList();

    public User(String pass, SuperHero superHero, int points, String username, int level, Place place) {
        super(username, level, place);
        this.pass = pass;
        this.superHero = superHero;
        this.points = points;
    }

    public void setListGems(ArrayList<Gem> listGems) {
        this.listGems = listGems;
    }

    public ArrayList<Gem> getListGems() {
        return listGems;
    }

    public User(String username) {
        super(username);
    }

    public User() {
    }

    public User(String pass, String username) {
        super(username);
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public SuperHero getSuperHero() {
        return superHero;
    }

    public int getPoints() {
        return points;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setSuperHero(SuperHero superHero) {
        this.superHero = superHero;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Develve un String con las gemas que hay en un sitio
     *
     * @return String
     */
    public String listGemsPlace() {
        StringBuilder sb = new StringBuilder("- Gems -");
        for (Gem g : listGems) {
            if (g.getPlace().equals(super.getPlace()) && g.getOwner() == null) {
                sb.append("\n").append(g);
            }
        }
        return sb.toString().equals("- Gems -") ? "There are no gems here" : sb.toString();
    }

}
