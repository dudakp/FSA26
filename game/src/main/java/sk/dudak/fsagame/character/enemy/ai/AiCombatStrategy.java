package sk.dudak.fsagame.character.enemy.ai;

import sk.dudak.fsagame.ability.AbilityId;
import sk.dudak.fsagame.character.enemy.Enemy;

public sealed interface AiCombatStrategy permits SimpleDamageRotation, SimpleRotationWithHealing {

    AbilityId selectNextAbility(Enemy self);

}
