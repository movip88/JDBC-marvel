package model;

import exceptions.ErroresLogica;
import java.util.Objects;

/**
 *
 * @author polmonleonvives
 */
public class Place {

    private String name;
    private String description;
    private Place north;
    private Place south;
    private Place east;
    private Place west;

    public Place(String name) {
        this.name = name;
    }

    public Place() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Si no tiene la direccion disponible lanza una excepcion
     *
     * @return - Place
     * @throws ErroresLogica
     */
    public Place getNorth() throws ErroresLogica {
        if (north == null) {
            throw new ErroresLogica(ErroresLogica.ERROR_DIRECTION);
        }
        return north;
    }

    /**
     * Si no tiene la direccion disponible lanza una excepcion
     *
     * @return - Place
     * @throws ErroresLogica
     */
    public Place getSouth() throws ErroresLogica {
        if (south == null) {
            throw new ErroresLogica(ErroresLogica.ERROR_DIRECTION);
        }
        return south;
    }

    /**
     * Si no tiene la direccion disponible lanza una excepcion
     *
     * @return - Place
     * @throws ErroresLogica
     */
    public Place getEast() throws ErroresLogica {
        if (east == null) {
            throw new ErroresLogica(ErroresLogica.ERROR_DIRECTION);
        }
        return east;
    }

    /**
     * Si no tiene la direccion disponible lanza una excepcion
     *
     * @return - Place
     * @throws ErroresLogica
     */
    public Place getWest() throws ErroresLogica {
        if (west == null) {
            throw new ErroresLogica(ErroresLogica.ERROR_DIRECTION);
        }
        return west;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNorth(Place north) {
        this.north = north;
    }

    public void setSouth(Place south) {
        this.south = south;
    }

    public void setEast(Place east) {
        this.east = east;
    }

    public void setWest(Place west) {
        this.west = west;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Place other = (Place) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Devuelve un String con las direcciones disponibles
     *
     * @return String
     */
    public String devolverDirecciones() {
        return "You can go:" + "\n" + (north != null ? "N " : "") + (south != null ? "S " : "") + (east != null ? "E " : "") + (west != null ? "W " : "");
    }

    @Override
    public String toString() {
        return "Place: " + name + "\n" + description;
    }

}
