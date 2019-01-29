package model;

/**
 * Clase encargada de la estructura de datos del ranking
 *
 * @author polmonleonvives
 */
public class RankingDTO {

    private User user;
    private SuperHero superhero;
    private int gems;

    public RankingDTO(User user, SuperHero superhero, int gems) {
        this.user = user;
        this.superhero = superhero;
        this.gems = gems;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(user.getUsername()).append(" ")
                .append(superhero.getName()).append(" ").append(gems).append(" ")
                .append(user.getLevel()).append(" ").append(user.getPoints()).toString();
    }
}
