package sk.dudak.fsagame.character.enemy.ai;

import sk.dudak.fsagame.ability.AbilityId;
import sk.dudak.fsagame.character.enemy.Enemy;

class SimpleDamageRotation implements AiCombatStrategy {

    @Override
    public AbilityId selectNextAbility(Enemy self) {
        return AbilityId.FIREBALL;
        // TODO: add roration logic
    }
}
