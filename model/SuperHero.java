package model;

/**
 * Clase encargada de almacenar la informacion de un ser heroe
 *
 * @author polmonleonvives
 */
public class SuperHero {
    private String name;
    private String superPower;

    public SuperHero(String name, String superPower) {
        this.name = name;
        this.superPower = superPower;
    }

    public SuperHero(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSuperPower() {
        return superPower;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }

    @Override
    public String toString() {
        return name + " -> " + superPower;
    }
    
    
}
