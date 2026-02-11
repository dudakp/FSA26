package sk.dudak.fsagame.game;

import sk.dudak.fsagame.character.enemy.Enemy;
import sk.dudak.fsagame.common.GameObjects;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

class EnemyWave implements GameObjects<Enemy> {

    private final Deque<Enemy> enemies;
    private Enemy currentEnemy;

    public EnemyWave(Collection<Enemy> enemies) {
        this.enemies = new ArrayDeque<>(enemies);
        this.currentEnemy = this.enemies.peek();
    }

    public Enemy removeCurrentEnemy() {
        Enemy killedEnemy = enemies.pop();
        currentEnemy = enemies.peek();
        return killedEnemy;
    }

    public Enemy getCurrentEnemy() {
        return currentEnemy;
    }

    @Override
    public void add(Enemy enemy) {
        enemies.add(enemy);
    }

    @Override
    public int size() {
        return enemies.size();
    }

    public boolean hasNextEnemy() {
        return !enemies.isEmpty();
    }

}
