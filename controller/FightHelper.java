package controller;

import java.util.Random;

/**
 * Es una classe que ayuda a la batalla contiene todos los posibles ataques en
 * una enumeración
 *
 * @author polmonleonvives
 */
public enum FightHelper {
    ROCK(0, "Rock"),
    PAPER(1, "Paper"),
    SCISSOR(2, "Scissor"),
    LIZARD(3, "Lizard"),
    SPOCK(4, "Spock");

    private int position;
    private String name;

    private FightHelper(int position, String name) {
        this.position = position;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    /**
     * devueleve un ataque aleatorio
     *
     * @return - FightHelper
     */
    public static FightHelper getRandomAttack() {
        FightHelper[] ataques = {ROCK, PAPER, SCISSOR, LIZARD, SPOCK};
        Random rand = new Random();
        return ataques[rand.nextInt(ataques.length)];
    }

    /**
     * Se le pasan dos ataques y te dice el resultado entre ellos devolviendo 1
     * si gana 0 si empata y -1 si pierde
     *
     * @param user - FightHelper
     * @param enemy - FightHelper
     * @return
     */
    public static int resolveBattle(FightHelper user, FightHelper enemy) {
        int[][][] resultados = {
            {{0}, {1}, {-1}, {-1}, {1}},
            {{1}, {0}, {-1}, {-1}, {1}},
            {{-1}, {1}, {0}, {1}, {-1}},
            {{-1}, {1}, {-1}, {0}, {1}},
            {{1}, {-1}, {1}, {-1}, {0}}
        };
        return resultados[user.getPosition()][enemy.getPosition()][0];
    }
}
