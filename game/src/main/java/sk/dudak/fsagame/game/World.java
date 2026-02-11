package sk.dudak.fsagame.game;

import sk.dudak.fsagame.ability.AbilityId;
import sk.dudak.fsagame.ability.AbilityNotLearnedException;
import sk.dudak.fsagame.character.Character;
import sk.dudak.fsagame.character.CharacterState;
import sk.dudak.fsagame.character.enemy.Enemy;
import sk.dudak.fsagame.character.enemy.EnemyFactory;
import sk.dudak.fsagame.character.hero.Hero;

import java.util.logging.Logger;
import java.util.stream.Stream;

public class World {

    private static final Logger LOG = Logger.getLogger(World.class.getName());

    private static World INSTANCE;

    private Hero hero;
    private Difficulty gameDifficulty;

    private EnemyFactory enemyFactory;
    private EnemyWave enemies;
    private Enemy activeEnemy;

    private World() {
    }

    public static World getInstance() {
        synchronized (World.class) {
            if (INSTANCE == null) {
                INSTANCE = new World();
            }
        }
        return INSTANCE;
    }

    public boolean heroTurn(AbilityId abilityToCast) throws AbilityNotLearnedException {
        LOG.info("Hero's turn");
        activeEnemy = enemies.getCurrentEnemy();
        LOG.info("Current target: %s. With health: %d".formatted(activeEnemy.getId(), activeEnemy.getHealth()));
        hero.useAbility(abilityToCast, activeEnemy);
        LOG.info("Active enemy now has %d health".formatted(activeEnemy.getHealth()));
        if (enemies.hasNextEnemy() && !activeEnemy.getId().equals(enemies.getCurrentEnemy().getId())) {
            activeEnemy = enemies.getCurrentEnemy();
            return true;
        }
        return false;
    }

    public void enemyTurn() {
        LOG.info("Enemy turn");
        activeEnemy.attackHero(hero);
    }

    public void addHero(Hero hero) {
        this.hero = hero;
    }

    public void setGameDifficulty(Difficulty difficulty) {
        this.gameDifficulty = difficulty;
        this.enemyFactory = new EnemyFactory(difficulty);
        this.enemies = new EnemyWave(Stream.iterate(0, i -> i < this.gameDifficulty.numOfEnemiesInWave, i -> i + 1)
                .map(i -> enemyFactory.generateEnemy())
                .toList()
        );
    }

    public void onEnemyDied() {
        Enemy killedEnemy = enemies.removeCurrentEnemy();
        hero.gainXp(1);
        LOG.info("enemy %s died! %d enemies remaining".formatted(killedEnemy, enemies.size()));
        // TODO: pridat ex pre Hero + zabezpecit aby dalsi enemy ktory sa spawne bol o nieco silnejsi
    }

    public void onHeroDied() {
        IO.println("game over");
    }

    public boolean isGameOver() {
        if (enemies.size() == 0) {
            return true;
        }
        return CharacterState.DEAD.equals(hero.getState());
    }

    public Character getActiveEnemy() {
        return enemies.getCurrentEnemy();
    }

    public int getRemainingEnemies() {
        return enemies.size();
    }
}
