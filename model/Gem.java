package model;

/**
 * Clase encargada de almacenar la informacion de una gema
 *
 * @author polmonleonvives
 */
public class Gem {

    private String name;
    private User user;
    private Entity owner;
    private Place place;

    public Gem(String name, User user, Entity owner, Place place) {
        this.name = name;
        this.user = user;
        this.owner = owner;
        this.place = place;
    }

    public Gem(String name) {
        this.name = name;
    }

    public Gem(String name, Place place) {
        this.name = name;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public Entity getOwner() {
        return owner;
    }

    public Place getPlace() {
        return place;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return name;
    }

}
