package model;

/**
 * Clase encargada de almacenar la informacion de una entidad
 *
 * @author polmonleonvives
 */
public class Entity {

    private String username;
    private int level;
    private Place place;

    public Entity(String username, int level, Place place) {
        this.username = username;
        this.level = level;
        this.place = place;
    }

    public Entity(String username) {
        this.username = username;
    }

    public Entity() {
    }

    public String getUsername() {
        return username;
    }

    public int getLevel() {
        return level;
    }

    public Place getPlace() {
        return place;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

}
