package sk.dudak.fsagame.character.enemy.ai;

import sk.dudak.fsagame.ability.AbilityId;
import sk.dudak.fsagame.character.enemy.Enemy;

final class SimpleRotationWithHealing implements AiCombatStrategy {
    @Override
    public AbilityId selectNextAbility(Enemy self) {
        return null;
    }
}
