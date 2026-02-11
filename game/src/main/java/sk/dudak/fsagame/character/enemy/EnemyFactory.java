package sk.dudak.fsagame.character.enemy;

import sk.dudak.fsagame.ability.AbilityId;
import sk.dudak.fsagame.character.enemy.ai.AiFactory;
import sk.dudak.fsagame.game.Difficulty;

import java.util.List;

public final class EnemyFactory {

    private final Difficulty difficulty;

    public EnemyFactory(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Enemy generateEnemy() {
        return switch (difficulty) {
            case EASY -> new Enemy(15, List.of(AbilityId.FIREBALL), AiFactory.INSTANCE.createAi(difficulty));
            case MEDIUM -> new Enemy(25, List.of(AbilityId.FIREBALL), AiFactory.INSTANCE.createAi(difficulty));
            case HARD ->
                    new Enemy(25, List.of(AbilityId.FIREBALL, AbilityId.LESSER_HEAL), AiFactory.INSTANCE.createAi(difficulty));
        };
    }

}
