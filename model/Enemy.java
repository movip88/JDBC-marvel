package model;

/**
 * Clase encargada de almacenar la informacion de un enemigo, su super clase es
 * entity
 *
 * @author polmonleonvives
 */
public class Enemy extends Entity {

    private String debility;

    public Enemy(String debility, String username, int level, Place place) {
        super(username, level, place);
        this.debility = debility;
    }

    public Enemy(String username) {
        super(username);
    }

    public String getDebility() {
        return debility;
    }

    public void setDebility(String debility) {
        this.debility = debility;
    }

    @Override
    public String toString() {
        return "Name: " + super.getUsername() + " - Debility: " + debility + " - Level: " + super.getLevel();
    }

}
