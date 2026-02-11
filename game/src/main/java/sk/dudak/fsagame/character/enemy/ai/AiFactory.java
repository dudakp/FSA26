package sk.dudak.fsagame.character.enemy.ai;

import sk.dudak.fsagame.game.Difficulty;

public enum AiFactory {
    INSTANCE;

    public AiCombatStrategy createAi(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY, MEDIUM -> new SimpleDamageRotation();
            case HARD -> new SimpleRotationWithHealing();
        };
    }

}
