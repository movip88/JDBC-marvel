package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import model.Enemy;
import model.Gem;
import model.User;
import persistence.MarstucomDao;

/**
 * Clase encargada de llevar la logica de una battalla entre un usuario y un
 * enemigo
 *
 * @author polmonleonvives
 */
public class BattleController {

    private int heroConAtacks;
    private int enemyConAtacks;
    private int heroTotalVictory;
    private int enemyTotalVictory;
    private StringBuilder respuesta;
    private User goodPlayer;
    private Enemy baadPlayer;
    private MarstucomDao instanceDao;

    public BattleController(User goodPlayer, Enemy baadPlayer, MarstucomDao instanceDao) {
        this.goodPlayer = goodPlayer;
        this.baadPlayer = baadPlayer;
        this.instanceDao = instanceDao;
        this.enemyConAtacks = baadPlayer.getLevel();
        this.heroConAtacks = goodPlayer.getLevel() + (goodPlayer.getSuperHero().getSuperPower().equals(baadPlayer.getDebility()) ? 1 : 0);
        this.heroTotalVictory = 0;
        this.enemyTotalVictory = 0;
        this.respuesta = new StringBuilder();
    }

    /**
     * Devuelve un StringBuilder con toda la información de la batalla
     *
     * @return - StringBuilder
     */
    public StringBuilder resultBattle() {
        return respuesta;
    }

    /**
     * Este metodo ejecuta la parte logica de la batalla y controla su duración
     * guardando la información de cada combate en el stringbuilder
     *
     * @return - BattleController
     */
    public BattleController runBattle() {
        while (continueBattle()) {
            respuesta.append(goodPlayer.getUsername()).append(": ").append(heroTotalVictory).append(" wins. - ").append(baadPlayer.getUsername()).append(": ").append(enemyTotalVictory).append(" wins.\n");
            respuesta.append("You have ").append(heroConAtacks).append(" attacks.").append("\n").append(baadPlayer.getUsername()).append(" has ").append(enemyConAtacks).append(" attacks.").append("\n");
            FightHelper heroAtack = FightHelper.getRandomAttack();
            FightHelper enemyAtack = FightHelper.getRandomAttack();
            respuesta.append(goodPlayer.getUsername()).append(" attack: ").append(heroAtack.getName()).append("\n").append(baadPlayer.getUsername()).append(" attack: ").append(enemyAtack.getName()).append("\n");
            switch (FightHelper.resolveBattle(heroAtack, enemyAtack)) {
                case 1:
                    respuesta.append(goodPlayer.getUsername()).append(" wins.\n");
                    heroTotalVictory++;
                    break;
                case 0:
                    respuesta.append("DRAW!!\n");
                    break;
                default:
                    respuesta.append(baadPlayer.getUsername()).append(" wins.\n");
                    enemyTotalVictory++;
            }
            heroConAtacks--;
            enemyConAtacks--;
        }
        return this;
    }

    /**
     * La funcion chekea si la partida vuele a iterarse otra vez o se ha acabado
     * 
     * @return boolean
     */
    private boolean continueBattle() {
        if ((heroTotalVictory == enemyTotalVictory && (enemyConAtacks >= 1 || heroConAtacks >= 1)) || (heroTotalVictory > enemyTotalVictory && enemyConAtacks >= 1) || (enemyTotalVictory > heroTotalVictory && heroConAtacks >= 1)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Este metodo se encarga de resolver la batalla saber quien ha ganado y
     * guardar los datos necesarios en la bbdd, guardanda la información en el
     * stringbuilder
     *
     * @return - BattleController
     * @throws SQLException
     */
    public BattleController resolveBattle() throws SQLException {
        respuesta.append("- Fight Finished! -\n");
        respuesta.append(goodPlayer.getUsername()).append(": ").append(heroTotalVictory).append(" wins. - ").append(baadPlayer.getUsername()).append(": ").append(enemyTotalVictory).append(" wins.\n");
        if (heroTotalVictory > enemyTotalVictory) {
            respuesta.append(goodPlayer.getUsername()).append(" wins.\n");
            ArrayList<Gem> gemsEnemy = instanceDao.getAllGemsOfEntityInPartyLazy(baadPlayer, goodPlayer);
            if (!gemsEnemy.isEmpty()) {
                respuesta.append("The enemy has lost their gems\n");
                respuesta.append(gemsEnemy.stream().map(Gem::getName).collect(Collectors.joining("\n", "", "\n")));
            }
            respuesta.append("You win 5 points.\n");
            goodPlayer.setPoints(goodPlayer.getPoints() + 5);
            if (goodPlayer.getPoints() > 50) {
                goodPlayer.setLevel(goodPlayer.getLevel() + 1);
                goodPlayer.setPoints(goodPlayer.getPoints() - 50);
            }
            instanceDao.enemyReleaseGems(goodPlayer, baadPlayer);
        } else if (enemyTotalVictory > heroTotalVictory) {
            respuesta.append(baadPlayer.getUsername()).append(" wins.\n");
            ArrayList<Gem> gemsUser = instanceDao.getAllGemsOfEntityInPartyLazy(goodPlayer, goodPlayer);
            if (!gemsUser.isEmpty()) {
                respuesta.append("the enemy has stolen your gems\n");
                respuesta.append(gemsUser.stream().map(Gem::getName).collect(Collectors.joining("\n", "", "\n")));
            }
            instanceDao.pasGemsUserToEnemy(goodPlayer, baadPlayer);
            respuesta.append("You lose 2 points\n");
            goodPlayer.setPoints(goodPlayer.getPoints() - 2 < 0 ? 0 : goodPlayer.getPoints() - 2);
        }
        respuesta.append("Your points: ").append(goodPlayer.getPoints()).append(" Your level: ").append(goodPlayer.getLevel()).append("\n");
        instanceDao.updateRandomPlaceEnemy(baadPlayer);
        instanceDao.updateLevelAndPointsUser(goodPlayer);
        respuesta.append(baadPlayer.getUsername()).append(" has disappeared");
        return this;
    }
}
