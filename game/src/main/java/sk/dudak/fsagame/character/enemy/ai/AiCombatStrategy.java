package sk.dudak.fsagame.character.enemy.ai;

import sk.dudak.fsagame.ability.AbilityId;
import sk.dudak.fsagame.character.enemy.Enemy;

public interface AiCombatStrategy {

    AbilityId selectNextAbility(Enemy self);

}
