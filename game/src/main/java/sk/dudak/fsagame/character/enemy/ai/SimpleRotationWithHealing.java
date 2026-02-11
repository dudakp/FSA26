package sk.dudak.fsagame.character.enemy.ai;

import sk.dudak.fsagame.ability.AbilityId;
import sk.dudak.fsagame.character.enemy.Enemy;

class SimpleRotationWithHealing implements AiCombatStrategy {
    @Override
    public AbilityId selectNextAbility(Enemy self) {
        return null;
    }
}
